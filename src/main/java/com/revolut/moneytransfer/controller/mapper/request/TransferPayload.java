package com.revolut.moneytransfer.controller.mapper.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransferPayload {
    @JsonProperty("debit_account_id")
    private final Long debitAccountId = null;
    @JsonProperty("credit_account_id")
    private final Long creditAccountId = null;
    @JsonProperty("amount")
    private final BigDecimal amount = null;

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
