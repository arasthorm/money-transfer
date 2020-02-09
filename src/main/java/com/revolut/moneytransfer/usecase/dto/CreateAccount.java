package com.revolut.moneytransfer.usecase.dto;

import java.math.BigDecimal;

public class CreateAccount {
    private final String number;
    private final BigDecimal balance;

    public CreateAccount(String number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
