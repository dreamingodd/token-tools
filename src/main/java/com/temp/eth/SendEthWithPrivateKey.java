package com.temp.eth;

import com.temp.eth.common.EthUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class SendEthWithPrivateKey extends EthAction {

    private static String from;
    private static String pk;
    private static String to;
    private static String value;

    public static void main(String[] args) throws Exception {

        init();
        parseArgs(args);

        BigInteger nonce = EthUtils.getNonce(web3j, from);
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                to,
                Convert.toWei(value, Convert.Unit.ETHER).toBigInteger()
        );
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(pk));
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        if (result.getError() == null) {
            System.out.println("Send Eth TX hash: " + result.getTransactionHash());
        } else {
            throw new Exception(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }


    private static void parseArgs(String[] args) {
        from = args[0];
        pk = args[1];
        to = args[2];
        value = args[3];
    }
}
