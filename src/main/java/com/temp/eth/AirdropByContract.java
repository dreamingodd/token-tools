package com.temp.eth;

import com.temp.common.Config;
import com.temp.common.CsvAddressParser;
import com.temp.eth.common.EthUtils;
import com.temp.eth.token.HttpClient;
import okhttp3.OkHttpClient;
import org.springframework.util.StringUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Air Drop for FT voting.
 * @author Ye_WD
 * @create 2018/7/2
 */
public class AirdropByContract {

    // Transaction count in a single batch transaction.
    private static final int TX_SIZE = 75;

    private static String filePath;
    private static String contractAddress;
    private static String tokenAddress;
    private static String decimals;
    private static String from;
    private static String pk;
    private static String value;

    private static Config config;
    private static Admin web3j;

    /**
     * Transfer tokenl
     * @param args 1.File Path of Airdrop addresses. 2.Function contract address. 3.Token contract address 4.Decimals. 5.Contract holder. 6.Amount.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        init(args);
        // get actual value
        Uint256 actualValue = new Uint256(new BigDecimal(value).multiply(new BigDecimal(10).pow(Integer.parseInt(decimals))).toBigInteger());
        // get Airdrop addresses
        List<Address> addresses = new ArrayList<>();
        List<String> addressStrs = CsvAddressParser.GetAddressFromLines(filePath);
        for (String addressStr : addressStrs) {
            Address address = new Address(addressStr);
            addresses.add(address);
        }
        int forCount = (addresses.size() - 1) / TX_SIZE;
        if (addresses.size() <= TX_SIZE) forCount = 0;
        BigInteger nonce = EthUtils.getNonce(web3j, from);
        for (int i = 0; i <= forCount; i++) {
            int start = TX_SIZE * i;
            int end = TX_SIZE * (i + 1) < addresses.size() ? TX_SIZE * (i + 1) : addresses.size();
            List<Address> tmpAddresses = addresses.subList(start, end);
            // you have to use DYNAMIC ARRAY ！！！！！！！！！！！！！！！！！！！！！！！！！！！！
            Array addressArray = new DynamicArray(tmpAddresses);
            RawTransaction rawTransaction = composeRawTransaction(actualValue, addressArray, nonce.add(BigInteger.valueOf(i)));
            // get signed hex value
            String hexValue = generateSig(rawTransaction);
            EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
            if (result.getError() == null) {
                System.out.println("Batch transfer tx hash: " + result.getTransactionHash());
            } else {
                throw new Exception(result.getError().getMessage());
            }
            Thread.sleep(120000);
        }
        System.out.println("---- Task End ----");
    }

    private static String generateSig(RawTransaction rawTransaction) throws Exception {
        BigInteger privateKey = BigInteger.ZERO;
        if (StringUtils.isEmpty(pk)) {
            privateKey = GetPrivateKey.getPrivateKey(from);
        } else {
            privateKey = new BigInteger(pk);
        }
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        return Numeric.toHexString(signedMessage);
    }

    private static RawTransaction composeRawTransaction(Uint256 actualValue, Array addressArray, BigInteger nonce) throws ExecutionException, InterruptedException {
        // compose function / transaction
        Function function = new Function(
                "multisend",
                Arrays.asList(new Address(tokenAddress), addressArray, actualValue),
                Collections.singletonList(new TypeReference<Bool>() {}));
        String encodedFunction = FunctionEncoder.encode(function);
        return RawTransaction.createTransaction(
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                contractAddress,
                encodedFunction);
    }

    private static void init(String[] args) throws IOException {
        parseArgs(args);
        config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Admin.build(httpService);
    }

    private static void parseArgs(String[] args) {
        filePath = args[0];
        contractAddress = args[1];
        tokenAddress = args[2];
        decimals = args[3];
        from = args[4];
        pk = args[5];
        value = args[6];
    }
}
