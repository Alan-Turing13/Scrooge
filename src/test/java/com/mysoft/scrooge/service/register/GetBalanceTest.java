package com.mysoft.scrooge.service.register;

import com.mysoft.scrooge.service.RegisterService;
import com.mysoft.scrooge.service.RegisterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetBalanceTest {

    private RegisterService registerService;

    @BeforeEach
    private void setUpService() {
        registerService = new RegisterServiceImpl();
    }

    @Test
    public void givenNoRegisters_WhenBalanceIsQueried_NothingIsReturned() {
        fail("Not implemented");
    }
}
