package com.mysoft.scrooge.service;

import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Override
    public String ping() {
        return "PONG!";
    }
}
