package com.temp.btc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import com.neemre.btcdcli4j.core.domain.Output;
import com.temp.btc.entity.OmniWalletAddressBalance;
import com.temp.btc.entity.TetherBalance;
import com.temp.common.Config;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BtcAction {

    protected static ObjectMapper objectMapper = new ObjectMapper();
    protected static Config config;
    protected static BtcdClient btcdClient;

    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        BtcdClient client = init();
        System.out.println(client.getBalance());
    }

    protected static BtcdClient init() throws IOException, BitcoindException, CommunicationException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(cm).build();
        config = new Config();
        btcdClient = new BtcdClientImpl(httpProvider, config.getProperties());
        return btcdClient;
    }

    protected static BigDecimal getBalance() throws BitcoindException, IOException, CommunicationException {
        init();
        return btcdClient.getBalance();
    }

    protected static List<OmniWalletAddressBalance> getOmniBalance() throws BitcoindException, IOException, CommunicationException {
        init();
        Object result = btcdClient.remoteCall("omni_getwalletaddressbalances", Arrays.asList());
        List<OmniWalletAddressBalance> omniBalanceList = objectMapper.readValue(result.toString(), new TypeReference<List<OmniWalletAddressBalance>>(){});
        return omniBalanceList;
    }

    protected static List<TetherBalance> getTetherBalance() throws BitcoindException, IOException, CommunicationException {
        init();
        List<OmniWalletAddressBalance> omniBalanceList = getOmniBalance();
        Integer tetherId = config.getTetherId();
        List<TetherBalance> tetherBalanceList = new ArrayList<>();
        for (OmniWalletAddressBalance omniWalletAddressBalance : omniBalanceList) {
            TetherBalance tetherBalance = TetherBalance.convert(tetherId, omniWalletAddressBalance);
            if (tetherBalance != null) tetherBalanceList.add(tetherBalance);
        }
        return tetherBalanceList;
    }

    protected static List<Output> listUnspent(List<String> addresses) throws BitcoindException, IOException, CommunicationException {
        init();
        if (addresses != null) {
            return btcdClient.listUnspent(0, 9999999, addresses);
        } else {
            return btcdClient.listUnspent();
        }
    }

    protected static String sendToAddress(String address, BigDecimal amount) throws BitcoindException, IOException, CommunicationException {
        init();
        return btcdClient.sendToAddress(address, amount);
    }
}
