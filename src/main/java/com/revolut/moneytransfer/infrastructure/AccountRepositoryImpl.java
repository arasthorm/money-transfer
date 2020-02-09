package com.revolut.moneytransfer.infrastructure;

import com.revolut.moneytransfer.domain.AbstractAccount;
import com.revolut.moneytransfer.domain.Account;
import com.revolut.moneytransfer.domain.AccountImpl;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public final class AccountRepositoryImpl implements AccountRepository {

    private final AtomicLong counter;
    private final ConcurrentMap<Long, Account> accounts;

    public AccountRepositoryImpl() {
        this.counter = new AtomicLong(0L);
        this.accounts = new ConcurrentHashMap<>();
    }

    @Override
    public Account getById(Long id) {
        return accounts.getOrDefault(id, AbstractAccount.getDefault());
    }

    @Override
    public void addAccount(String number, BigDecimal balance) {
        final Account account = new AccountImpl(
                this.counter.incrementAndGet(),
                number,
                balance
        );
        accounts.putIfAbsent(account.getId(), account);
    }
}
