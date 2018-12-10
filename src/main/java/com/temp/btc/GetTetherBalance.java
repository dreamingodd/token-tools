package com.temp.btc;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.temp.btc.entity.TetherBalance;

import java.io.IOException;
import java.util.List;

public class GetTetherBalance extends BtcAction {

    private static String address;

    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        List<TetherBalance> tetherBalanceList = getTetherBalance();

        parseArgs(args);
        // Print
        for (TetherBalance tetherBalance : tetherBalanceList) {
            if (address == null) {
                System.out.println(tetherBalance);
            } else {
                if (address.equals(tetherBalance.getAddress())) {
                    System.out.println(tetherBalance);
                }
            }
        }
    }


    private static void parseArgs(String[] args) {
        if (args.length > 0) {
            address = args[0];
        }
    }
}
