package com.revolut.moneytransfer.usecase;

import com.revolut.moneytransfer.domain.Exceptions.InsufficientFundsException;
import com.revolut.moneytransfer.usecase.dto.CreateAccount;
import com.revolut.moneytransfer.usecase.dto.Transfer;

public interface Bank {
    void transferMoney(Transfer payload) throws InsufficientFundsException,
            NullPointerException, IllegalArgumentException;
    void createAccount(CreateAccount payload) throws NullPointerException, IllegalArgumentException;
}
