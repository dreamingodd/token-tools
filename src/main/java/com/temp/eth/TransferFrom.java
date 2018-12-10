package com.temp.eth;

import com.temp.eth.common.EthUtils;
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
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class TransferFrom extends EthAction {

    private static String contractAddress;
    private static String from;
    private static String sender;
    private static String value;

    public static void main(String[] args) throws Exception {
        init();
        parseArgs(args);
        // Trillion
        Uint256 limit = new Uint256(new BigInteger(value).multiply(BigInteger.TEN.pow(18)));
        String to = sender;
        Function function = new Function(
                "transferFrom",
                Arrays.asList(new Address(from), new Address(to), limit),
                Collections.singletonList(new TypeReference<Bool>() {}));
        BigInteger nonce = EthUtils.getNonce(web3j, sender);
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                contractAddress,
                encodedFunction);
        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(sender));
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        if (result.getError() == null) {
            System.out.println("Approve TX hash: " + result.getTransactionHash());
        } else {
            throw new Exception(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");

    }

    private static void parseArgs(String[] args) {
        contractAddress = args[0];
        from = args[1];
        sender = args[2];
        value = args[3];
    }
}
