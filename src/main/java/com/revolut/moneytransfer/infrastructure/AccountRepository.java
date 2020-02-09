package com.revolut.moneytransfer.infrastructure;

import com.revolut.moneytransfer.domain.Account;

import java.math.BigDecimal;

public interface AccountRepository {
    Account getById(Long id);
    void addAccount(String number, BigDecimal balance);
}
