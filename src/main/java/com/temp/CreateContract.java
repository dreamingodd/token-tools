package com.temp;

import com.temp.common.Config;
import com.temp.common.EthUtils;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class CreateContract {

    private static String from;
    private static String path;

    private static Config config;
    private static Admin web3j;

    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        init(args);
        unlockAccount();
        BigInteger nonce = EthUtils.getNonce(web3j, from);
        Transaction transaction = Transaction.createContractTransaction(
                from,
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                BigInteger.ZERO,
                getFibonacciSolidityBinary(path));
        EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();
        String transactionHash = transactionResponse.getTransactionHash();
        System.out.println("Transaction hash: " + transactionHash);
        TransactionReceipt transactionReceipt = waitForTransactionReceipt(transactionHash);
        String contractAddress = transactionReceipt.getContractAddress();
        System.out.println("Contract Address: " + contractAddress);
        System.out.println("---- Task End ----");
    }


    private static void unlockAccount() throws Exception {
        PersonalUnlockAccount result = web3j.personalUnlockAccount(from, config.get("ethPass")).sendAsync().get();
        if (result.getError() != null) {
            System.out.println(result.getError().getCode() + " | " + result.getError().getMessage());
            throw new Exception("Unlock account failed!");
        }
    }

    private static void init(String[] args) throws IOException {
        parseArgs(args);
        config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Admin.build(httpService);
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

    private static TransactionReceipt waitForTransactionReceipt(
            String transactionHash) throws Exception {

        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, 15000, 40);

        if (!transactionReceiptOptional.isPresent()) {
            System.out.println("Transaction receipt not generated after " + 40 + " attempts");
        }

        return transactionReceiptOptional.get();
    }

    private static void parseArgs(String[] args) {
        from = args[0];
        path = args[1];
    }
}
