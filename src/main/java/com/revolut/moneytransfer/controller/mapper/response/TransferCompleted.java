package com.revolut.moneytransfer.controller.mapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransferCompleted {

    @JsonProperty("debit_account_id")
    private final Long debitAccountId;
    @JsonProperty("credit_account_id")
    private final Long creditAccountId;
    @JsonProperty("amount")
    private final BigDecimal amount;

    public TransferCompleted(Long debitAccountId,
                             Long creditAccountId,
                             BigDecimal amount) {
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