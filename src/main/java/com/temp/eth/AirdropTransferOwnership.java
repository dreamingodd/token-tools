package com.temp.eth;

import com.temp.eth.common.Config;
import com.temp.eth.common.EthUtils;
import com.temp.eth.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;


/**
 * Air Drop for FT voting.
 * @author Ye_WD
 * @create 2018/7/2
 */
public class AirdropTransferOwnership {

    private static String contractAddress;
    private static String from;
    private static String to;

    /**
     * Transfer tokenl
     * @param args 1.Contract address. 2.Contract owner. 3.New Contract ownver
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Web3j web3j = Web3j.build(httpService);
        // compose function
        Function function = new Function(
                "transferOwnership",
                Arrays.asList(new Address(to)),
                Collections.singletonList(new TypeReference<Bool>() {}));
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
        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(from));
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        if (result.getError() == null) {
            System.out.println("Token transfer tx hash: " + result.getTransactionHash());
        } else {
            throw new Exception(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }

    private static void parseArgs(String[] args) {
        contractAddress = args[0];
        from = args[1];
        to = args[2];
    }
}
