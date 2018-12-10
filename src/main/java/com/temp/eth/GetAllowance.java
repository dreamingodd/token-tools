package com.temp.eth;

import com.temp.eth.common.EthUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class GetAllowance extends EthAction {

    private static String contractAddress;
    private static String owner;
    private static String spender;

    public static void main(String[] args) throws Exception {
        init();
        parseArgs(args);
        // Trillion
        Function function = new Function(
                "allowance",
                Arrays.asList(new Address(owner), new Address(spender)),
                Collections.singletonList(new TypeReference<Bool>() {}));
        BigInteger nonce = EthUtils.getNonce(web3j, owner);
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                contractAddress,
                encodedFunction);
        ECKeyPair ecKeyPair = ECKeyPair.create(GetPrivateKey.getPrivateKey(owner));
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        if (result.getError() == null) {
            System.out.println("Allowance TX hash: " + result.getTransactionHash());
        } else {
            throw new Exception(result.getError().getMessage());
        }
        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(result.getTransactionHash(), SLEEP_DURATION, ATTEMPTS);

        if (!transactionReceiptOptional.isPresent()) {
            System.out.println("Transaction receipt not generated after " + ATTEMPTS + " attempts");
        }
        System.out.println("Tx Result: " + transactionReceiptOptional.get());
        System.out.println("---- Task End ----");

    }
    private static void parseArgs(String[] args) {
        contractAddress = args[0];
        owner = args[1];
        spender = args[2];
    }
}
