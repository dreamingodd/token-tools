package com.temp;

import com.temp.common.Config;
import com.temp.common.EthUtils;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class TransferToken {

    private static String from;
    private static String decimals;
    private static String to;
    private static String contractAddress;
    private static String value;

    /**
     * Transfer tokenl
     * @param args 1.Contract address. 2.Decimals. 3.Token holder. 4.Token receiver. 5.Amount.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Admin admin = Admin.build(httpService);
        Web3j web3j = Web3j.build(httpService);
        // get actual value
        Uint256 actualValue = new Uint256(new BigDecimal(value).multiply(new BigDecimal(10).pow(Integer.parseInt(decimals))).toBigInteger());
        // compose function
        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(to), actualValue),
                Collections.singletonList(new TypeReference<Bool>() {}));
        BigInteger nonce = EthUtils.getNonce(web3j, from);
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                contractAddress,
                encodedFunction);
        // get ALICE
//        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(from));
        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(from));
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        if (result.getError() == null) {
            System.out.println("Token transfer tx hash: " + result.getTransactionHash());
        } else {
            throw new Exception(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }

    private static void parseArgs(String[] args) {
        contractAddress = args[0];
        decimals = args[1];
        from = args[2];
        to = args[3];
        value = args[4];
    }
}
