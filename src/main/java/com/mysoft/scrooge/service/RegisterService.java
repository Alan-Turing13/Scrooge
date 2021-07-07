package com.mysoft.scrooge.service;

import com.mysoft.scrooge.model.Register;

import java.util.List;

public interface RegisterService {

    String ping();
    List<Register> getBalance();
}
