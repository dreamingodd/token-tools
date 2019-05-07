package com.temp.eth;

import com.temp.eth.common.EthUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class ReplaceTransaction extends EthAction {


    private static String from;
    private static String pk;

    /**
     * 1.Address. 2.Pk.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        init();
        parseArgs(args);

        BigInteger nonce = EthUtils.getNonce(web3j, from);
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce,
                config.getGasPrice(),
                config.getGasLimit(),
                from,
                BigInteger.ZERO
        );
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(pk));
        Credentials ALICE = Credentials.create(ecKeyPair);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        if (result.getError() == null) {
            System.out.println("Replace TX hash: " + result.getTransactionHash());
        } else {
            throw new Exception(result.getError().getMessage());
        }
        System.out.println("---- Task End ----");
    }

    private static void parseArgs(String[] args) {
        from = args[0];
        pk = args[1];
    }
}
