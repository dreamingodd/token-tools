package com.temp.btc;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.temp.btc.entity.OmniWalletAddressBalance;

import java.io.IOException;
import java.util.List;

public class GetOmniBalance extends BtcAction {
    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        List<OmniWalletAddressBalance> omniBalanceList = getOmniBalance();

        // Print
        for (OmniWalletAddressBalance omniBalance : omniBalanceList) {
            System.out.println(omniBalance);
        }
    }
}
