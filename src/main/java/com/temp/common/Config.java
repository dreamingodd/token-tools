package com.temp.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Properties;

public class Config {

    private Properties properties;

    public Config() throws IOException {
        Properties fixedProperties = new Properties();
        fixedProperties = new Properties();
        fixedProperties.load(new FileInputStream("src/main/resources/properties.properties"));
        properties = new Properties();
        properties.load(new FileInputStream((String) properties.get("configPath")));
    }

    public String get(String key) {
        return (String) properties.get(key);
    }

    public BigInteger getGethPrice() {
        return new BigInteger((String) properties.get("gethPrice"));
    }

    public BigInteger getGethLimit() {
        return new BigInteger((String) properties.get("gethLimit"));
    }

    public void show() {
        Enumeration enum1 = properties.propertyNames();//得到配置文件的名字
        while(enum1.hasMoreElements()) {
            String strKey = (String) enum1.nextElement();
            String strValue = properties.getProperty(strKey);
            System.out.println(strKey + "=" + strValue);
        }
    }

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.show();
        System.out.println(config.get("gethPath"));
    }
}
