package com.temp;

import com.temp.common.Config;
import com.temp.token.ContractService;
import com.temp.token.HttpClient;
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
 * Air Drop for FT voting.
 * Not very safe but direct.
 * @author Ye_WD
 * @create 2018/6/30
 */
public class AirDropWithoutSign {


    public static String filePath;
    public static String contractAddress;
    public static String from;
    public static String quantity;

    public static Quorum quorum;
    private static Web3j web3j;
    private static Admin admin;

    /**
     * @param args 1.File Path. 2.Contract Address. 3.Token sender. 4.Quantity.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");
        List<String> failAddresses = new ArrayList<>();
        List<String> zeroAddresses = new ArrayList<>();

        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        admin = Admin.build(httpService);
        quorum = Quorum.build(httpService);
        web3j = Web3j.build(httpService);
        ContractService contractService = new ContractService(quorum, web3j, new NodeConfiguration());
        for (String to : CsvAddressParser.GetAddressFromLines(filePath)) {
            try {
                BigInteger balance = contractService.balanceOf(contractAddress, to);
                if (balance.compareTo(BigInteger.TEN) > 0) {
                    sendToken(httpService, BigDecimal.valueOf(Integer.parseInt(quantity)), to, contractService, admin, web3j, config);
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
        System.out.println("---- Task End ----");
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

    private static void sendToken(HttpService httpService, BigDecimal bigDecimal, String to, ContractService contractService, Admin admin, Web3j web3j, Config config) throws Exception {
        BigInteger value = Convert.toWei(bigDecimal, Convert.Unit.ETHER).toBigInteger();
        org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function("transfer", Arrays.<Type>asList(new Address(to), new Uint256(Numeric.decodeQuantity(Numeric.encodeQuantity(value)))), Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        admin.personalUnlockAccount(from, config.get("ethPass")).send();
        Transaction transaction = new org.web3j.protocol.core.methods.request.Transaction(
                from,
                null,
                config.getGasPrice(),
                config.getGasLimit(),
                contractAddress,
                value,
                data
        );
        EthSendTransaction result = contractService.eth_sendTransaction(httpService, transaction, contractAddress, config.getGasPrice(), config.getGasLimit(), to);
        System.err.println(String.format("[%s]:%s", to, result.getTransactionHash()));
    }

    private static void parseArgs(String[] args) {
        filePath = args[0];
        contractAddress = args[1];
        from = args[2];
        quantity = args[3];
    }

}
