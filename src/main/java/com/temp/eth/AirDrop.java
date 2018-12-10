package com.temp.eth;

import com.temp.common.Config;
import com.temp.common.CsvAddressParser;
import com.temp.eth.common.EthUtils;
import com.temp.eth.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Air Drop for FT voting.
 * @author Ye_WD
 * @create 2018/6/30
 */
public class AirDrop {


    public static String filePath;
    public static String contractAddress;
    public static String from;
    public static String quantity;

    private static Web3j web3j;

    /**
     * @param args 1.File Path. 2.Contract Address. 3.Token sender. 4.Quantity.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");
        List<String> failAddresses = new ArrayList<>();
        List<String> zeroAddresses = new ArrayList<>();

        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Web3j.build(httpService);

        for (String to : CsvAddressParser.GetAddressFromLines(filePath)) {
            // get Private Key
            BigInteger privateKey = GetPrivateKey.getPrivateKey(from);
            // get actual value
            Uint256 actualValue = new Uint256(new BigDecimal(Integer.parseInt(quantity)).multiply(new BigDecimal(10).pow(18)).toBigInteger());
            // compose function
            Function function = new Function(
                    "transfer",
                    Arrays.asList(new Address(to), actualValue),
                    Collections.singletonList(new TypeReference<Bool>() {
                    }));
            BigInteger nonce = EthUtils.getNonce(web3j, from);
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    config.getGasPrice(),
                    config.getGasLimit(),
                    contractAddress,
                    encodedFunction);
            // get ALICE
            //        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(from));
            ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
            Credentials ALICE = Credentials.create(ecKeyPair);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
            if (result.getError() == null) {
                System.out.print("Token transfer to address: " + to);
                System.out.println(" | Token transfer tx hash: " + result.getTransactionHash());
            } else {
                throw new Exception(result.getError().getMessage());
            }
        }
        System.out.println("---- Task End ----");
    }

    private static void parseArgs(String[] args) {
        filePath = args[0];
        contractAddress = args[1];
        from = args[2];
        quantity = args[3];
    }

}
