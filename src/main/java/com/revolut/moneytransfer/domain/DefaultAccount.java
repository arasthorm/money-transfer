package com.revolut.moneytransfer.domain;

import java.math.BigDecimal;

public class DefaultAccount extends AbstractAccount {

    private static Long defaultId = -1L;

    private static final DefaultAccount INSTANCE = new DefaultAccount();
    private DefaultAccount() {
        super(defaultId,"",BigDecimal.ZERO);
    }
    protected static Account getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return (obj instanceof DefaultAccount);
    }

    @Override
    public int hashCode() {
        return defaultId.intValue();
    }
}
