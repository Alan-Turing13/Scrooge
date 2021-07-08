package com.mysoft.scrooge.service;

public class InvalidRegisterOperationException extends Exception {

    private final long registerId;

    public InvalidRegisterOperationException(long registerId, String message) {
        this.registerId = registerId;
    }

    public long getRegisterId() {
        return registerId;
    }
}
