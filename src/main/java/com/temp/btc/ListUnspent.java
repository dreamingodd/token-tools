package com.temp.btc;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.domain.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListUnspent extends BtcAction {

    private static List<String> addresses;

    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        parseArgs(args);
        List<Output> unspentList = listUnspent(addresses);
        for (Output unspent : unspentList) {
            System.out.println(unspent);
        }
    }

    private static void parseArgs(String[] args) {
        if (args.length > 0) {
            addresses = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                addresses.add(args[i]);
            }
        }
    }
}
