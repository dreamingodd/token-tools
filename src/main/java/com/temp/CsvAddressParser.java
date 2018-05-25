package com.temp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvAddressParser {

    public static List<String> GetAddressFromLines(String filePath) {

        List<String> addresses = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String address = "";
            while ((address = reader.readLine()) !=  null) {
                addresses.add(address);
            }
            return addresses;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        GetAddressFromLines("");
    }
}
