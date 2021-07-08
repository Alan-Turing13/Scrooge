package com.mysoft.scrooge.service;

public class RegisterNotFoundException extends Exception {

    private final long registerId;

    public RegisterNotFoundException(long registerId, String message) {
        this.registerId = registerId;
    }

    public RegisterNotFoundException(long registerId) {
        this(registerId, String.format("Register id=%d does not exist", registerId));
    }

    public long getRegisterId() {
        return registerId;
    }
}
