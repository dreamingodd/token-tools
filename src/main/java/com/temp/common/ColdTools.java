package com.temp.common;

import com.alibaba.fastjson.JSON;
import lombok.Cleanup;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;

public class ColdTools {

    public static void jsonToCsvs(String filepath, String folderpath) throws IOException {
        File file = new File(filepath);
        @Cleanup InputStream is = new FileInputStream(file);
        String jsonStr = IOUtils.toString(is);
        List<Address> list = JSON.parseArray(jsonStr, Address.class);
        System.out.println(list);
        String fileStr = "";
        for (Address address : list) {
            if (list.indexOf(address) % 75 == 0) {
                if (!StringUtils.isEmpty(fileStr)) {
                    File csvFile = new File(folderpath + "\\account" + list.indexOf(address) / 75 + ".csv");
                    @Cleanup FileWriter fw = new FileWriter(csvFile);
                    fw.write(fileStr);
                }
                fileStr = "";
            }
            fileStr += address.getAddress() + "\n";
        }
    }

    public static void jsonToCsv(String filepath, String folderpath) throws IOException {
        File file = new File(filepath);
        @Cleanup InputStream is = new FileInputStream(file);
        String jsonStr = IOUtils.toString(is);
        List<Address> list = JSON.parseArray(jsonStr, Address.class);
        System.out.println(list);
        String fileStr = "";
        for (Address address : list) {
            fileStr += address.getAddress() + "\n";
        }
        File csvFile = new File(folderpath + "\\account.csv");
        @Cleanup FileWriter fw = new FileWriter(csvFile);
        fw.write(fileStr);
    }

    public static void main(String[] args) throws IOException {
        jsonToCsv("E:\\project\\github\\token-tools\\src\\test\\resources\\airdropmvc\\account_74.json", "E:\\project\\github\\token-tools\\src\\test\\resources\\airdropmvc");
//        jsonToCsvs("E:\\project\\github\\token-tools\\src\\test\\resources\\airdropmvc\\account_74.json", "E:\\project\\github\\token-tools\\src\\test\\resources\\airdropmvc");
    }
}
