package com.revolut.moneytransfer.controller.mapper.request;

import java.math.BigDecimal;

public class CreateAccountPayload {
    private final String number = null;
    private final BigDecimal balance = null;

    public String getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
