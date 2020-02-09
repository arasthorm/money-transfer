package com.revolut.moneytransfer.domain;

import java.math.BigDecimal;

public class AccountImpl extends AbstractAccount {

    public AccountImpl(Long id, String number, BigDecimal balance) {
        super(id, number, balance);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AccountImpl)) {
            return false;
        }
        Account a = (AccountImpl) obj;
        return a.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
