package com.temp.eth;

import com.temp.common.Config;
import com.temp.eth.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class EthAction {

    protected static final int SLEEP_DURATION = 15000;
    protected static final int ATTEMPTS = 40;

    protected static Config config;
    protected static Admin web3j;

    protected static void init() throws IOException {
        config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Admin.build(httpService);
    }


    protected static Optional<TransactionReceipt> getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts) throws Exception {

        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration);
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                break;
            }
        }

        return receiptOptional;
    }

    protected static Optional<TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws Exception {
        EthGetTransactionReceipt transactionReceipt =
                web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get();

        return transactionReceipt.getTransactionReceipt();
    }

    protected static String getTransactionByHash(String hash) throws IOException, ExecutionException, InterruptedException {
        init();
        web3j.ethGetTransactionByHash(hash).sendAsync().get();
        return null;
    }
}
