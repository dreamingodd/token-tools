package com.temp.eth;

import com.temp.eth.common.Config;
import com.temp.eth.common.EthUtils;
import com.temp.eth.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class SignTokenTransfer {

    private static String contractAddress;
    private static String from;
    private static String to;
    private static String value;

    private static Config config;
    private static Admin web3j;

    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        init(args);

        BigInteger privateKey = GetPrivateKey.getPrivateKey(from);
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        Credentials ALICE = Credentials.create(ecKeyPair);
        BigInteger nonce = EthUtils.getNonce(web3j, from);
        Uint256 actualValue = new Uint256(new BigDecimal(value).multiply(new BigDecimal(BigInteger.TEN.pow(18))).toBigInteger());
        Function function = new Function("transfer", Arrays.asList(new Address(to), actualValue), Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                contractAddress,
                encodedFunction);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        System.out.println(hexValue);
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
        contractAddress = args[0];
        from = args[1];
        to = args[2];
        value = args[3];
    }

}
