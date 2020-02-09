package com.revolut.moneytransfer.usecase.dto;

import java.math.BigDecimal;

public class Transfer {
    private final Long debitAccountId;
    private final Long creditAccountId;
    private final BigDecimal amount;

    public Transfer(Long debitAccountId, Long creditAccountId, BigDecimal amount) {
        this.debitAccountId = debitAccountId;
        this.creditAccountId = creditAccountId;
        this.amount = amount;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
