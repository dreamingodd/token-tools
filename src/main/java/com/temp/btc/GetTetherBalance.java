package com.temp.btc;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.temp.btc.entity.TetherBalance;

import java.io.IOException;
import java.util.List;

public class GetTetherBalance extends BtcAction {
    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        List<TetherBalance> tetherBalanceList = getTetherBalance();

        // Print
        for (TetherBalance tetherBalance : tetherBalanceList) {
            System.out.println(tetherBalance);
        }
    }
}
