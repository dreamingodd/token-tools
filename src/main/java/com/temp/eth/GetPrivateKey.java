package com.temp.eth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temp.eth.common.Config;
import com.temp.eth.common.TextSearchFile;
import org.apache.commons.io.FileUtils;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.math.BigInteger;

public class GetPrivateKey {

    public static void main(String[] args) throws Exception {
        System.out.println(getPrivateKey(args[0]));
    }

    public static BigInteger getPrivateKey(String address) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = new Config();
        File[] fileArr = TextSearchFile.searchFile(new File(config.get("keystorePath")), address.substring(2));
        if (fileArr.length == 0) throw new Exception("No such ether address in the keystore.");
        File keyStore = fileArr[0];
        String source = FileUtils.readFileToString(keyStore);
        WalletFile file = objectMapper.readValue(source, WalletFile.class);
        ECKeyPair ecKeyPair = Wallet.decrypt(config.get("ethPass"), file);
        return ecKeyPair.getPrivateKey();
    }
}
