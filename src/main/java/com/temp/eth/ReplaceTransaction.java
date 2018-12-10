package com.temp.eth;

import com.temp.eth.common.Config;
import com.temp.eth.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

public class ReplaceTransaction {


    private static String from;
    private static String nonce;

    private static Config config;
    private static Admin web3j;

    /**
     * 1.Address. 2.Nonce.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        init(args);

        BigInteger privateKey = GetPrivateKey.getPrivateKey(from);
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        Credentials ALICE = Credentials.create(ecKeyPair);
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
            new BigInteger(nonce),
            config.getGasPrice(),
            config.getGasLimit(),
            from,
            BigInteger.ZERO
        );
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

    private static void init(String[] args) throws IOException {
        parseArgs(args);
        config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Admin.build(httpService);
    }

    private static void parseArgs(String[] args) {
        from = args[0];
        nonce = args[1];
    }
}
