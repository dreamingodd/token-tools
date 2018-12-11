package com.temp.btc;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.temp.btc.entity.TetherBalance;

import java.io.IOException;
import java.util.List;

public class GetTetherBalance extends BtcAction {

    private static String address;

    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        parseArgs(args);
        // Print
        if (address == null) {
            List<TetherBalance> tetherBalanceList = getTetherBalance();
            for (TetherBalance tetherBalance : tetherBalanceList) {
                System.out.println(tetherBalance);
            }
        } else {
            TetherBalance tetherBalance = getTetherBalance(address);
            System.out.println(tetherBalance);
        }
    }


    private static void parseArgs(String[] args) {
        if (args.length > 0) {
            address = args[0];
        }
    }
}
