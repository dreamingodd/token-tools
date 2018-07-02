package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class CreateContract {

    private static String from;
    private static Web3j web3j;

    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Admin admin = Admin.build(httpService);
        web3j = Web3j.build(httpService);

        System.out.println("---- Task End ----");
    }

    private static String getFibonacciSolidityBinary(String path) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    private static Optional<TransactionReceipt> getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts) throws Exception {

        Optional<TransactionReceipt> receiptOptional = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get().getTransactionReceipt();
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration);
                receiptOptional = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get().getTransactionReceipt();
            } else {
                break;
            }
        }

        return receiptOptional;
    }

    private static void parseArgs(String[] args) {
        from = args[0];
    }
}
