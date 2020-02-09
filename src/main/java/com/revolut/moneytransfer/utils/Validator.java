package com.revolut.moneytransfer.utils;

import com.revolut.moneytransfer.domain.Account;

import java.math.BigDecimal;

public class Validator {

    public static void validateAmountNotNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static void validateAmountPositive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static void validateAccountsAreValid(Account debit, Account credit) {
        if (debit.isDefault()) {
            throw new IllegalArgumentException("Debit account must be valid");
        }

        if (credit.isDefault()) {
            throw new IllegalArgumentException("Credit account must be valid");
        }
    }

    public static void validateAccountsAreDifferent(Account debit, Account credit) {
        if (debit.equals(credit)) {
            throw new IllegalArgumentException("Accounts must be different");
        }
    }
}
