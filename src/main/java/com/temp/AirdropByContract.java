package com.temp;

import com.temp.common.Config;
import com.temp.common.EthUtils;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
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

    private static String filePath;
    private static String contractAddress;
    private static String tokenAddress;
    private static String decimals;
    private static String from;
    private static String value;

    private static Config config;
    private static Admin web3j;

    /**
     * Transfer tokenl
     * @param args 1.Airdrop addresses. 2.Function contract address. 3.Token contract address 4.Decimals. 5.Contract holder. 6.Amount.
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

        // compose function / transaction
        // you have to use DYNAMIC ARRAY ！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        Array addressArray = new DynamicArray(addresses);
        RawTransaction rawTransaction = composeRawTransaction(actualValue, addressArray);
        // get signed hex value
        String hexValue = generateSig(rawTransaction);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        if (result.getError() == null) {
            System.out.println("Token transfer tx hash: " + result.getTransactionHash());
        } else {
            throw new Exception(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }

    private static String generateSig(RawTransaction rawTransaction) throws Exception {
        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(from));
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        return Numeric.toHexString(signedMessage);
    }

    private static RawTransaction composeRawTransaction(Uint256 actualValue, Array addressArray) throws ExecutionException, InterruptedException {
        Function function = new Function(
                "multisend",
                Arrays.asList(new Address(tokenAddress), addressArray, actualValue),
                Collections.singletonList(new TypeReference<Bool>() {}));
        BigInteger nonce = EthUtils.getNonce(web3j, from);
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
        value = args[5];
    }
}
