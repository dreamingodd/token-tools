package com.temp.token;

import org.web3j.protocol.core.methods.request.Transaction;

import java.math.BigInteger;
import java.util.List;

/**
 * PrivateTransaction
 *
 * @author qiyichen
 * @create 2018/3/29 17:46
 */
public class CPrivateTransaction extends Transaction {
    private List<String> privateFor;

    public CPrivateTransaction(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, List<String> privateFor) {
        super(from, nonce, gasPrice, gasLimit, to, value, data);
        this.privateFor = privateFor;
    }

    public List<String> getPrivateFor() {
        return this.privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }
}
