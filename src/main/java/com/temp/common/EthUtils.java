package com.temp.common;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class EthUtils {

    public static BigInteger getNonce(Web3j web3j, String address) throws ExecutionException, InterruptedException {

        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                address, DefaultBlockParameterName.PENDING).sendAsync().get();

        return ethGetTransactionCount.getTransactionCount();
    }
}
