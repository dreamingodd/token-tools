package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;

public class SendEth {
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Admin admin = Admin.build(httpService);
        Web3j web3j = Web3j.build(httpService);
        PersonalUnlockAccount flag = admin.personalUnlockAccount(config.get("ethFrom"), config.get("ethFromPass")).send();
        if (flag.getError() != null) throw new Exception("Eth send - " + flag.getError().getMessage());
        Transaction transaction = new Transaction(
                config.get("ethFrom"),
                null,
                config.getGethPrice(),
                config.getGethLimit(),
                config.get("ethTo"),
                Convert.toWei(config.get("ethSendValue"), Convert.Unit.ETHER).toBigInteger(),
                null
        );
        EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
        System.out.println("Eth send TxHash: " + ethSendTransaction.getTransactionHash());
    }
}
