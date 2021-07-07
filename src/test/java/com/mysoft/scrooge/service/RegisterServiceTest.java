package com.mysoft.scrooge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {

    private RegisterService registerService;

    @BeforeEach
    private void setUpService() {
        registerService = new RegisterServiceImpl();
    }

    @Test
    public void testPing() {
        assertEquals("PONG!", registerService.ping());
    }
}
