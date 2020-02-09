package com.revolut.moneytransfer.domain;

import java.math.BigDecimal;

public interface Account {
    Long getId();
    BigDecimal getBalance();
    void credit(BigDecimal amount) throws NullPointerException, IllegalArgumentException;
    void debit(BigDecimal amount) throws NullPointerException, IllegalArgumentException;
    boolean isDefault();
}
