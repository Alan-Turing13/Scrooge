package com.mysoft.scrooge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Register {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String displayName;
    private BigDecimal balance = BigDecimal.ZERO;

    protected Register() {}

    public Register(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return String.format("Register[id=%d, displayName='%s']", id, displayName);
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
