package com.mysoft.scrooge.service.register;

import com.mysoft.scrooge.persistence.RegisterRepository;
import com.mysoft.scrooge.service.RegisterService;
import com.mysoft.scrooge.service.RegisterServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.configuration.IMockitoConfiguration;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class GetBalanceTest {

    private RegisterService registerService;

    @Mock
    private RegisterRepository registerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        registerService = new RegisterServiceImpl(registerRepository);
    }

    @Test
    public void givenNoRegisters_WhenBalanceIsQueried_NothingIsReturned() {
        doReturn(Collections.emptyList()).when(registerRepository).findAll();
        var registers = registerService.getBalance();

        assertTrue(registers.isEmpty());
    }
}
