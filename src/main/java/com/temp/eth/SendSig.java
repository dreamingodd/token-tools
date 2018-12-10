package com.temp.eth;

import com.temp.common.Config;
import com.temp.eth.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class SendSig {

    private static String sig;

    private static Config config;
    private static Admin web3j;

    public static void main(String[] args) throws IOException {

        init(args);
        EthSendTransaction result = web3j.ethSendRawTransaction(sig).send();
        if (result.getError() == null) {
            System.out.println(result.getTransactionHash());
        } else {
            System.out.println(result.getError().getMessage());
        }
    }

    private static void init(String[] args) throws IOException {
        parseArgs(args);
        config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Admin.build(httpService);
    }

    private static void parseArgs(String[] args) {
        sig = args[0];
    }


}
