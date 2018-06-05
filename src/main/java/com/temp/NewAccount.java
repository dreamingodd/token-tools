package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class NewAccount {
    public static void main(String[] args) throws IOException {
        System.out.println("---- Task Begin ----");
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Admin admin = Admin.build(httpService);
        NewAccountIdentifier result = admin.personalNewAccount(config.get("ethPassNew")).send();
        if (result.getError() == null) {
            System.out.println("New Account Address: " + result.getAccountId());
        } else {
            System.out.println(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }
}
