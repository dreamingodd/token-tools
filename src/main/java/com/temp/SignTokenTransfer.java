package com.temp;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

public class SignTokenTransfer {

    public static void main(String[] args) throws Exception {

//        sendTxBySign("0xf8a901848321560083419ce094513306af847d12aa868e7e34132207605fc4f85f80b844a9059cbb000000000000000000000000cfba103e72bc4d25f2c0691b912ddd28629602eb00000000000000000000000000000000000000000000000000000000000334501ba073fe25e07c78766f1068d912187303c92fbd9fbd1be4f60bc021fb58784ba854a0280dd111fb75749e45f7b79e6835666b07beb71cee8fcd5aeff758d51abcd924");
//        sendTxBySign("0xf8a903848321560083419ce094756b20f28bddb4a251e623de59c829d078fe062480b844a9059cbb000000000000000000000000cfba103e72bc4d25f2c0691b912ddd28629602eb000000000000000000000000000000000000000000000000004a9b63844880001ba05f06276605f354ae445b579b4fc8e56f434d820cd646f853276615e2a70cb517a0589dcb6ebfe29fc1f8b57e59b2ac301010890bc5c9015b53e852abf12583acec");
        sendTxViaRaw();
    }

    private static void sendTxViaRaw() throws Exception {

        String mvcContract = "0x513306af847d12aa868e7e34132207605fc4f85f";
        String privateKey = "";
        String to = "0x61a4bee43d6fd14e415166a6b0f4beeb2e293d22";
        BigDecimal value = new BigDecimal(10.1);
        Web3j web3j = Web3j.build(new HttpService("http://192.168.213.170:8545"));
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey));
        Credentials ALICE = Credentials.create(ecKeyPair);
        BigInteger nonce = new BigInteger("1");
        Function function = new Function("transfer", Arrays.<Type>asList(new Address(to), new Uint256(value.multiply(new BigDecimal(100L)).toBigInteger())), Collections.<TypeReference<?>>emptyList());
        String data =  FunctionEncoder.encode(function);
        RawTransaction transaction = RawTransaction.createContractTransaction(nonce, GAS_PRICE.divide(BigInteger.valueOf(10)), GAS_LIMIT, BigInteger.ZERO, data);
        byte[] signedMessage = TransactionEncoder.signMessage(transaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
        System.out.println(result);
    }

    private static void sendTxBySign(String sign) throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.213.170:8545"));
        EthSendTransaction result = web3j.ethSendRawTransaction(sign).send();
        System.out.println(result);
    }
}
