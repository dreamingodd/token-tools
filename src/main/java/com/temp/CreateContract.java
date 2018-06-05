package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
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
    private static Web3j web3j;

    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Admin admin = Admin.build(httpService);
        web3j = Web3j.build(httpService);
        PersonalUnlockAccount result1 = admin.personalUnlockAccount(from, config.get("ethPass")).send();
        if (result1.getError() != null) throw new Exception("Unlock - " + result1.getError().getMessage());
        EthGetTransactionCount result2 = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
        if (result2.getError() != null) throw new Exception("Unlock - " + result2.getError().getMessage());
        Transaction transaction = Transaction.createContractTransaction(
                from,
                result2.getTransactionCount(),
                config.getGethPrice(),
                config.getGethLimit(),
                BigInteger.ZERO,
                getFibonacciSolidityBinary(config.get("solidityBinaryPath")));
        EthSendTransaction result3 = web3j.ethSendTransaction(transaction).send();
        if (result3.getError() == null) {
            System.out.println("Deploy contract TxHash: " + result3.getTransactionHash());
        } else {
            System.out.println("Deploy contract error: " + result3.getError().getMessage());
        }

        System.out.println("---- Task End ----");
    }

    private static String getFibonacciSolidityBinary(String path) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    private static void parseArgs(String[] args) {
        from = args[0];
    }
    private Optional<TransactionReceipt> getTransactionReceipt(
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
}
