package com.temp.eth;

import com.temp.eth.common.Config;
import com.temp.eth.token.HttpClient;
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
    private static String value;

    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");
        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Admin admin = Admin.build(httpService);
        Web3j web3j = Web3j.build(httpService);
        PersonalUnlockAccount flag = admin.personalUnlockAccount(from, config.get("ethPass")).send();
        if (flag.getError() != null) throw new Exception("Eth send - " + flag.getError().getMessage());
        Transaction transaction = new Transaction(
                from,
                null,
                config.getGasPrice(),
                config.getGasLimit(),
                to,
                Convert.toWei(value, Convert.Unit.ETHER).toBigInteger(),
                null
        );
        EthSendTransaction result = web3j.ethSendTransaction(transaction).send();
        if (result.getError() == null) {
            System.out.println("Eth send TxHash: " + result.getTransactionHash());
        } else {
            System.out.println(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }

    private static void parseArgs(String[] args) {
        from = args[0];
        to = args[1];
        value = args[2];
    }
}
