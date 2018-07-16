package com.temp;

import com.temp.common.Config;
import com.temp.token.HttpClient;
import com.temp.token.HumanStandardToken;
import okhttp3.OkHttpClient;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

import static com.temp.token.HumanStandardToken.deploy;


public class CreateToken {

    private static String creatorAddress;
    private static String tokenName;
    private static String tokenSymbol;
    // 自动加18个零
    private static String amount;

    private static Config config;
    private static Admin web3j;

    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        init(args);
        BigInteger privateKey = GetPrivateKey.getPrivateKey(creatorAddress);
        Credentials creator = Credentials.create(ECKeyPair.create(privateKey));
        BigInteger totalSupply = new BigInteger(amount).multiply(BigInteger.TEN.pow(18));
        HumanStandardToken contract = deploy(web3j, creator, config.getGasPrice(), config.getGasLimit(),
                totalSupply, tokenName, BigInteger.valueOf(18), tokenSymbol).send();
        Assert.isTrue(contract.isValid(), "Invalid Contract!");
        Assert.isTrue(contract.totalSupply().send().equals(totalSupply), "Total supply is wrong!");
        System.out.println("Contract address: " + contract.getContractAddress());

        System.out.println("---- Task End ----");
    }

    private static void init(String[] args) throws IOException {
        parseArgs(args);
        config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        web3j = Admin.build(httpService);
    }

    private static void parseArgs(String[] args) {
        creatorAddress = args[0];
        tokenName = args[1];
        tokenSymbol = args[2];
        amount = args[3];
    }
}
