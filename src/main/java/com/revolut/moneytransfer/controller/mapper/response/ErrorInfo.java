package com.revolut.moneytransfer.controller.mapper.response;


public class ErrorInfo {

    private final String message;

    public ErrorInfo(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
