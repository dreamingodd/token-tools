package com.temp.btc;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;

import java.io.IOException;

public class GetNewAddress extends BtcAction {

    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        System.out.println(getNewAddress());
    }
}
