package com.temp;

import com.temp.token.ContractService;
import com.temp.token.NodeConfiguration;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * temp
 *
 * @author qiyichen
 * @create 2018/3/29 16:04
 */
public class AirDrop {


    public static String filePath = "D:\\MVC\\air-drop-OLE-0.csv";
    static String gethUrl = "https://mvchain.xyz/";
    static String contractAddress = "0x9d9223436dDD466FC247e9dbbD20207e640fEf58";
    static String mainAccount = "0xe268d8c22739b9abdabdfb14763ff60d0de5e3ba";
    static BigInteger GAS_PRICE = Contract.GAS_PRICE.divide(BigInteger.valueOf(5));
    static BigInteger GAS_LIMIT = Contract.GAS_LIMIT;


    public static void main(String[] args) throws Exception {
        List<String> failAddresses = new ArrayList<>();
        List<String> zeroAddresses = new ArrayList<>();
        HttpService httpService = new HttpService(gethUrl, generateOkHttpClient(), false);
        Quorum quorum = Quorum.build(httpService);
        Admin admin = Admin.build(httpService);
        Web3j web3j = Web3j.build(httpService);
        ContractService contractService = new ContractService(quorum, web3j, new NodeConfiguration());
        for (String to : CsvAddressParser.GetAddressFromLines(filePath)) {
            try {
                BigInteger balance = contractService.balanceOf(contractAddress, to);
                if (balance.equals(BigInteger.ZERO)) {
                    sendToken(httpService, BigDecimal.valueOf(10), to, contractService, admin, web3j);
                    zeroAddresses.add(to);
                }
            } catch (Exception e) {
                e.printStackTrace();
                failAddresses.add(to);
            }

        }
        System.out.println("Failed:");
        for (String address : failAddresses) {
            System.out.println(address);
        }
        System.out.println("\n\n\n\nZERO:");
        for (String address : zeroAddresses) {
            System.out.println(address);
        }
    }

    public static OkHttpClient generateOkHttpClient() throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent = originalRequest.newBuilder()
                                .build();
                        return chain.proceed(requestWithUserAgent);
                    }
                });
        return builder.build();
    }

    private static void sendToken(HttpService httpService, BigDecimal bigDecimal, String to, ContractService contractService, Admin admin, Web3j web3j) throws Exception {
        BigInteger value = Convert.toWei(bigDecimal, Convert.Unit.ETHER).toBigInteger();
        org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function("transfer", Arrays.<Type>asList(new Address(to), new Uint256(Numeric.decodeQuantity(Numeric.encodeQuantity(value)))), Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        admin.personalUnlockAccount(mainAccount, "mvc123$%^").send();
        Transaction transaction = new org.web3j.protocol.core.methods.request.Transaction(
                mainAccount,
                null,
                GAS_PRICE,
                GAS_LIMIT,
                contractAddress,
                value,
                data
        );
        EthSendTransaction result = contractService.eth_sendTransaction(httpService, transaction, contractAddress, GAS_PRICE, GAS_LIMIT, to);
        System.err.println(String.format("[%s]:%s", to, result.getTransactionHash()));
    }

}
