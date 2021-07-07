package com.mysoft.scrooge.service.register;

import com.mysoft.scrooge.model.Register;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

public class GetBalanceTest extends RegisterTestBase {

    @Test
    public void givenNoRegisters_WhenBalanceIsQueried_NothingIsReturned() {
        doReturn(Collections.emptyList()).when(registerRepository).findAll();
        var registers = registerService.getBalance();

        assertTrue(registers.isEmpty());
    }

    @Test
    public void givenSingleRegister_WhenBalanceIsQueried_TheRegisterIsReturned() {

        var theRegister = new Register("Test register");
        theRegister.setBalance(BigDecimal.valueOf(42));

        doReturn(List.of(theRegister)).when(registerRepository).findAll();
        var registers = registerService.getBalance();

        assertEquals(1, registers.size());
        assertEquals("Test register", registers.get(0).getDisplayName());
        assertEquals(BigDecimal.valueOf(42),  registers.get(0).getBalance());
    }

    //TODO: add tests for ordering when specified
}
