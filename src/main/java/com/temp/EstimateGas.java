package com.temp;

import com.temp.common.Config;
import com.temp.common.EthUtils;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class EstimateGas extends EthAction {

    private static String contractAddress;
    private static String from;
    private static String to;

    public static void main(String[] args) throws IOException {
        System.out.println("---- Task Begin ----");
        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Web3j web3j = Web3j.build(httpService);

        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(to), new Uint256(BigInteger.TEN.pow(18).multiply(new BigInteger("1")))),
                Collections.singletonList(new TypeReference<Bool>() {}));
        String encodedFunction = FunctionEncoder.encode(function);
        EthEstimateGas result = web3j.ethEstimateGas(
                Transaction.createEthCallTransaction(from, contractAddress, encodedFunction)).send();
        if (result.getError() == null) {
            System.out.println("Estimate Transfer Amount: " + result.getAmountUsed());
        } else {
            System.out.println(result.getError().getMessage());
        }
        Uint256 limit = new Uint256(BigInteger.TEN.pow(18).multiply(new BigInteger("100000000000")));
        function = new Function(
                "approve",
                Arrays.asList(new Address(to), limit),
                Collections.singletonList(new TypeReference<Bool>() {}));
        encodedFunction = FunctionEncoder.encode(function);
        result = web3j.ethEstimateGas(
                Transaction.createEthCallTransaction(from, contractAddress, encodedFunction)).send();
        if (result.getError() == null) {
            System.out.println("Estimate Approve Amount: " + result.getAmountUsed());
        } else {
            System.out.println(result.getError().getMessage());
        }
    }

    private static void parseArgs(String[] args) {
        contractAddress = args[0];
        from = args[1];
        to = args[2];
    }
}
