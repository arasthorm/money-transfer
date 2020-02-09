package com.revolut.moneytransfer.infrastructure;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class AccountRepositoryTest {

    @Test
    public void AccountRepository() {
        AccountRepository repo = new AccountRepositoryImpl();
        repo.addAccount("1", BigDecimal.ZERO);
        assertNotNull(repo.getById(1L));
        repo.addAccount("2", BigDecimal.ZERO);
        assertNotNull(repo.getById(2L));
        assertTrue(repo.getById(3L).isDefault());
    }
}
