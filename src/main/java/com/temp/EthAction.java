package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class EthAction {

    protected static final int SLEEP_DURATION = 15000;
    protected static final int ATTEMPTS = 40;

    protected static Config config;
    protected static Admin web3j;

    public static void init() throws IOException {
        config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Admin.build(httpService);
    }
}
