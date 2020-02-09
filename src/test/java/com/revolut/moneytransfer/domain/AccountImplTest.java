package com.revolut.moneytransfer.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountImplTest {

    @Test
    public void AccountConstructor() {
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> new AccountImpl(null, null, null));
        assertEquals("Id cannot be null", e.getLocalizedMessage());

        e = assertThrows(NullPointerException.class,
                () -> new AccountImpl(1L, null, null));
        assertEquals("number cannot be null", e.getLocalizedMessage());

        e = assertThrows(NullPointerException.class,
                () -> new AccountImpl(1L, "2", null));
        assertEquals("Balance cannot be null", e.getLocalizedMessage());

        IllegalArgumentException i = assertThrows(IllegalArgumentException.class,
                () -> new AccountImpl(1L, "2", new BigDecimal(-1)));
        assertEquals("Amount cannot be negative", i.getLocalizedMessage());
    }

    @Test
    public void Credit() {
        Account account = new AccountImpl(1L, "1", BigDecimal.ZERO);
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> account.credit(null));
        assertEquals("Amount cannot be null", e.getLocalizedMessage());

        IllegalArgumentException i = assertThrows(IllegalArgumentException.class,
                () -> account.credit(new BigDecimal(-1)));
        assertEquals("Amount cannot be negative", i.getLocalizedMessage());

        account.credit(BigDecimal.ONE);
        assertEquals(account.getBalance(), BigDecimal.ONE);
    }

    @Test
    public void Debit() {
        Account account = new AccountImpl(1L, "1", BigDecimal.ONE);
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> account.debit(null));
        assertEquals("Amount cannot be null", e.getLocalizedMessage());

        IllegalArgumentException i = assertThrows(IllegalArgumentException.class,
                () -> account.debit(new BigDecimal(-1)));
        assertEquals("Amount cannot be negative", i.getLocalizedMessage());

        account.debit(BigDecimal.ONE);
        assertEquals(account.getBalance(), BigDecimal.ZERO);
    }
}
