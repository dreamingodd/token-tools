package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class EstimateGas {

    private static String from;
    private static String to;

    public static void main(String[] args) throws IOException {
        System.out.println("---- Task Begin ----");
        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Web3j web3j = Web3j.build(httpService);
        EthEstimateGas result = web3j.ethEstimateGas(
                Transaction.createEthCallTransaction(from, to, "0x0")).send();
        if (result.getError() == null) {
            System.out.println("Estimate amount: " + result.getAmountUsed());
        } else {
            System.out.println(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }

    private static void parseArgs(String[] args) {
        from = args[0];
        to = args[1];
    }
}
