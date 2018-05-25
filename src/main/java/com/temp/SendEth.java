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

public class SendEth {

    private static String from;
    private static String to;
    private static String password;
    private static String value;

    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");
        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Admin admin = Admin.build(httpService);
        Web3j web3j = Web3j.build(httpService);
        PersonalUnlockAccount flag = admin.personalUnlockAccount(from, password).send();
        if (flag.getError() != null) throw new Exception("Eth send - " + flag.getError().getMessage());
        Transaction transaction = new Transaction(
                from,
                null,
                config.getGethPrice(),
                config.getGethLimit(),
                to,
                Convert.toWei(value, Convert.Unit.ETHER).toBigInteger(),
                null
        );
        EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
        System.out.println("Eth send TxHash: " + ethSendTransaction.getTransactionHash());
        System.out.println("---- Task End ----");
    }

    private static void parseArgs(String[] args) {
        from = args[0];
        to = args[1];
        password = args[2];
        value = args[3];
    }
}
