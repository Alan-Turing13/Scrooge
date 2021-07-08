package com.mysoft.scrooge.service;

import java.math.BigDecimal;

public class InvalidMonetaryValueException extends Exception {

    private final BigDecimal amount;

    public InvalidMonetaryValueException(BigDecimal amount, String message) {
        this.amount = amount;
    }

    public BigDecimal getRegisterId() {
        return amount;
    }
}
