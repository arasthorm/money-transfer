package com.revolut.moneytransfer.usecase;

import com.revolut.moneytransfer.usecase.dto.CreateAccount;
import com.revolut.moneytransfer.usecase.dto.Transfer;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankImplTest {

    @Test
    public void TransferConditions() {
        final Bank bank = BankImpl.getInstance();

        NullPointerException e = assertThrows(NullPointerException.class,
                () -> bank.transferMoney(null));
        assertEquals("Payload data cannot be null", e.getLocalizedMessage());

        e = assertThrows(NullPointerException.class,
                () -> bank.transferMoney( new Transfer(null, null, null)));
        assertEquals("Debit account cannot be null", e.getLocalizedMessage());

        e = assertThrows(NullPointerException.class,
                () -> bank.transferMoney(new Transfer(1L, null, null)));
        assertEquals("Credit account cannot be null", e.getLocalizedMessage());

        e = assertThrows(NullPointerException.class,
                () -> bank.transferMoney(new Transfer(1L, 2L, null)));
        assertEquals("Amount cannot be null", e.getLocalizedMessage());

        IllegalArgumentException i = assertThrows(IllegalArgumentException.class,
                () -> bank.transferMoney(new Transfer(1L, 2L, BigDecimal.ZERO)));
        assertEquals("Amount must be greater than zero", i.getLocalizedMessage());

        i = assertThrows(IllegalArgumentException.class,
                () -> bank.transferMoney(new Transfer(100L, 2L, BigDecimal.ONE)));
        assertEquals("Debit account must be valid", i.getLocalizedMessage());

        bank.createAccount(new CreateAccount("1", BigDecimal.ZERO));

        i = assertThrows(IllegalArgumentException.class,
                () -> bank.transferMoney(new Transfer(1L, 100L, BigDecimal.ONE)));
        assertEquals("Credit account must be valid", i.getLocalizedMessage());

        i = assertThrows(IllegalArgumentException.class,
                () -> bank.transferMoney(new Transfer(1L, 1L, BigDecimal.ONE)));
        assertEquals("Accounts must be different", i.getLocalizedMessage());
    }

    @Test
    public void CreateAccountConditions() {
        final Bank bank = BankImpl.getInstance();

        NullPointerException e = assertThrows(NullPointerException.class,
                () -> bank.createAccount(null));
        assertEquals("Payload data cannot be null", e.getLocalizedMessage());

        e = assertThrows(NullPointerException.class,
                () -> bank.createAccount(new CreateAccount(null, null)));
        assertEquals("Number cannot be null", e.getLocalizedMessage());

        e = assertThrows(NullPointerException.class,
                () -> bank.createAccount(new CreateAccount("1", null)));
        assertEquals("Balance cannot be null", e.getLocalizedMessage());

        IllegalArgumentException i = assertThrows(IllegalArgumentException.class,
                () -> bank.createAccount(new CreateAccount("1", new BigDecimal(-1))));
        assertEquals("Amount cannot be negative", i.getLocalizedMessage());
    }
}
