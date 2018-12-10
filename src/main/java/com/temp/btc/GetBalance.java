package com.temp.btc;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;

import java.io.IOException;

public class GetBalance extends BtcAction {


    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        BtcdClient client = init();
        System.out.println(client.getBalance());
    }
}
