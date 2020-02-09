package com.revolut.moneytransfer.domain;

import com.revolut.moneytransfer.utils.Validator;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractAccount implements Account{

    private final Long id;
    private final String number;
    private BigDecimal balance;
    private final transient Lock lock;

    protected AbstractAccount(Long id, String number, BigDecimal balance) {
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(number, "number cannot be null");
        Objects.requireNonNull(balance, "Balance cannot be null");
        Validator.validateAmountNotNegative(balance);

        this.id = id;
        this.number = number;
        this.balance = balance;
        this.lock = new ReentrantLock();
    }

    public Long getId() {
        return this.id;
    }

    public String getNumber() { return this.number; }

    public BigDecimal getBalance() {
        try {
            lock.lock();
            return this.balance;
        } finally {
            lock.unlock();
        }
    }

    public void credit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        Validator.validateAmountNotNegative(amount);
        try {
            lock.lock();
            this.balance = this.getBalance().add(amount);
        } finally {
            lock.unlock();
        }

    }

    public void debit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        Validator.validateAmountNotNegative(amount);
        try {
            lock.lock();
            this.balance = this.getBalance().subtract(amount);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isDefault() {
        return this.equals(getDefault());
    }

    public static Account getDefault() {
        return DefaultAccount.getInstance();
    }

    @Override
    public String toString() {
        return String.format("Account{id=%d, balance=%s}", id, balance);
    }
}
