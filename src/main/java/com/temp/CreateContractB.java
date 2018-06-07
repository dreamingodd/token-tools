package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import com.temp.token.YwdSub;
import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.abi.datatypes.Address;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.temp.token.YwdSub.deploy;

public class CreateContractB {

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
//        PersonalUnlockAccount result1 = admin.personalUnlockAccount(from, config.get("ethPass")).send();
//        if (result1.getError() != null) throw new Exception("Unlock - " + result1.getError().getMessage());
//        EthGetTransactionCount result2 = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
//        if (result2.getError() != null) throw new Exception("Unlock - " + result2.getError().getMessage());
//        Transaction transaction = Transaction.createContractTransaction(
//                from,
//                result2.getTransactionCount(),
//                config.getGethPrice(),
//                config.getGethLimit(),
//                BigInteger.ZERO,
//                getFibonacciSolidityBinary(config.get("solidityBinaryPath")));
//        EthSendTransaction result3 = web3j.ethSendTransaction(transaction).send();
//        if (result3.getError() == null) {
//            System.out.println("Deploy contract TxHash: " + result3.getTransactionHash());
//        } else {
//            System.out.println("Deploy contract error: " + result3.getError().getMessage());
//            throw new Exception("Deploy - " + result2.getError().getMessage());
//        }
//        String txHash = result3.getTransactionHash();
//        TransactionReceipt result4 = getTransactionReceipt(txHash, 15000, 15).get();
//        if (result4.getGasUsed().equals(config.getGethLimit())) {
//            throw new Exception("Contract execution ran out of gas");
//        }
//        String contractAddress = result4.getContractAddress();
//        System.out.println("Contract address: " + contractAddress);
        // get ALICE
        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(from));
        Credentials ALICE = Credentials.create(ecKeyPair);
        YwdSub contract = deploy(web3j, ALICE, config.getGethPrice(), config.getGethLimit()).send();
        System.out.println("Contract address: " + contract.getContractAddress());
        System.out.println(contract.isValid());
        System.out.println(contract.totalSupply().get());
        System.out.println(contract.balanceOf(new Address(from)));
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
