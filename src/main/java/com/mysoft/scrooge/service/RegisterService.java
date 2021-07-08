package com.mysoft.scrooge.service;

import com.mysoft.scrooge.model.Register;

import java.math.BigDecimal;
import java.util.List;

public interface RegisterService {

    String ping();
    List<Register> getBalance();
    void recharge(long registerId, BigDecimal amount) throws RegisterNotFoundException, InvalidRegisterOperationException;
}
