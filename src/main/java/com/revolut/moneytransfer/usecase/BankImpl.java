package com.revolut.moneytransfer.usecase;

import com.revolut.moneytransfer.domain.Account;
import com.revolut.moneytransfer.domain.Exceptions.InsufficientFundsException;
import com.revolut.moneytransfer.infrastructure.AccountRepositoryImpl;
import com.revolut.moneytransfer.infrastructure.AccountRepository;
import com.revolut.moneytransfer.usecase.dto.CreateAccount;
import com.revolut.moneytransfer.usecase.dto.Transfer;
import com.revolut.moneytransfer.utils.Validator;

import java.util.Objects;

public final class BankImpl implements Bank{

    private final AccountRepository accountRepo;

    private static final Bank INSTANCE = new BankImpl(new AccountRepositoryImpl());
    private BankImpl(AccountRepository accountRepo) {
        Objects.requireNonNull(accountRepo, "Account repository cannot be null");
        this.accountRepo = accountRepo;
    }
    public static Bank getInstance() {
        return INSTANCE;
    }

    public void transferMoney(Transfer payload)
            throws NullPointerException, IllegalArgumentException, InsufficientFundsException {
        Objects.requireNonNull(payload, "Payload data cannot be null");
        Objects.requireNonNull(payload.getDebitAccountId(), "Debit account cannot be null");
        Objects.requireNonNull(payload.getCreditAccountId(), "Credit account cannot be null");
        Objects.requireNonNull(payload.getAmount(), "Amount cannot be null");
        Validator.validateAmountPositive(payload.getAmount());

        Account debit = accountRepo.getById(payload.getDebitAccountId());
        Account credit = accountRepo.getById(payload.getCreditAccountId());
        Validator.validateAccountsAreValid(debit, credit);
        Validator.validateAccountsAreDifferent(debit, credit);

        class Helper {
            private void transfer() throws InsufficientFundsException {
                if (debit.getBalance().compareTo(payload.getAmount()) < 0){
                    throw new InsufficientFundsException("Insufficient funds from debit account");
                } else {
                    debit.debit(payload.getAmount());
                    credit.credit(payload.getAmount());
                }
            }
        }

        // Avoid deadlock if we receive concurrent requests from account A to account B,
        // and from account B to account A
        if (debit.getId() < credit.getId()) {
            synchronized (debit) {
                synchronized (credit) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (credit) {
                synchronized (debit) {
                    new Helper().transfer();
                }
            }
        }
    }

    public void createAccount(CreateAccount payload) {
        Objects.requireNonNull(payload, "Payload data cannot be null");
        Objects.requireNonNull(payload.getNumber(), "Number cannot be null");
        Objects.requireNonNull(payload.getBalance(), "Balance cannot be null");
        Validator.validateAmountNotNegative(payload.getBalance());

        this.accountRepo.addAccount(payload.getNumber(), payload.getBalance());
    }
}
