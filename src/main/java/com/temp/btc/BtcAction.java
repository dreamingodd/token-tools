package com.temp.btc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import com.temp.common.Config;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;

public class BtcAction {

    protected static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        BtcdClient client = init();
        System.out.println(client.getBalance());
    }

    protected static BtcdClient init() throws IOException, BitcoindException, CommunicationException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(cm).build();
        Config config = new Config();
        BtcdClient client = new BtcdClientImpl(httpProvider, config.getProperties());
        return client;
    }
}
