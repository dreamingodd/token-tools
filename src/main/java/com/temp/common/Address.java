package com.temp.common;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class Address {

    private BigInteger id;
    private String tokenType;
    private String address;
    private Integer used;
    private BigDecimal balance;
    private BigInteger userId;
    private String addressType;
    private Integer approve;
}
