package com.revolut.moneytransfer.domain.Exceptions;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
