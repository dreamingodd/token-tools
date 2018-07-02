package com.temp;

import com.temp.common.Config;
import com.temp.common.EthUtils;
import com.temp.token.HttpClient;
import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
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
import java.util.List;


/**
 * Air Drop for FT voting.
 * @author Ye_WD
 * @create 2018/7/2
 */
public class AirdropByContract {

    private static String from;
    private static String decimals;
    private static String contractAddress;
    private static String value;

    /**
     * Transfer tokenl
     * @param args 1.Contract address. 2.Decimals. 3.Contract holder. 5.Amount.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("---- Task Begin ----");

        parseArgs(args);
        Config config = new Config();
        OkHttpClient okHttpClient = HttpClient.generateOkHttpClient();
        HttpService httpService = new HttpService(config.get("gethUrl"), okHttpClient, false);
        Web3j web3j = Web3j.build(httpService);
        // get actual value
        Uint256 actualValue = new Uint256(new BigDecimal(value).multiply(new BigDecimal(10).pow(Integer.parseInt(decimals))).toBigInteger());
        // compose function
//        Array addresses = new StaticArray(Arrays.asList(new Address("0x12bb047117b8452fa0ad41885e2a3fbc949a489a"), new Address("0x35b2ca5161b7720bee657902cc6bb854a7c97a80")));
        Array addresses = new StaticArray(Arrays.asList(
                new Address("0x12bb047117b8452fa0ad41885e2a3fbc949a489a"),
                new Address("0xcfBa103e72BC4D25F2c0691b912Ddd28629602EB"),
                new Address("0x35b2ca5161b7720bee657902cc6bb854a7c97a80")));
        Function function = new Function(
                "multisend",
                Arrays.asList(new Address("0x55ee4f8972a511ccba5de915835a5f501615486b"), addresses, actualValue),
                Collections.singletonList(new TypeReference<Bool>() {}));
        BigInteger nonce = EthUtils.getNonce(web3j, from);
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                config.getGethPrice(),
                config.getGethLimit(),
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
        value = args[3];
    }
}
