package com.temp.btc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.temp.btc.entity.OmniWalletAddressBalance;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GetOmniBalance extends BtcAction {
    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        BtcdClient client = init();
        Object result = client.remoteCall("omni_getwalletaddressbalances", Arrays.asList());
        List<OmniWalletAddressBalance> omniBalanceList = objectMapper.readValue(result.toString(), new TypeReference<List<OmniWalletAddressBalance>>(){});
        // Print
        for (OmniWalletAddressBalance omniBalance : omniBalanceList) {
            System.out.println(omniBalance);
        }
    }
}
