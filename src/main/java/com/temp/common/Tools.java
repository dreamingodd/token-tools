package com.temp.common;

import java.math.BigInteger;

public class Tools {

    public static void main(String[] args) {
        String str16 = new BigInteger("113190373812679565619350641616985228926059494378690433376530069939728513074228", 10).toString(16);
        System.out.println(str16);
    }
}
