package com.mysoft.scrooge.service.register;

import com.mysoft.scrooge.model.Register;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class RechargeRegisterTest extends RegisterTestBase {

    @Test
    public void givenAnEmptyRegister_WhenRechargedWith13_ThenBalanceOf13IsPersisted() {

        var theRegister = new Register(1,"Test register");
        doReturn(Optional.of(theRegister)).when(registerRepository).findById(1L);

        assertDoesNotThrow( () ->
            registerService.recharge(1L, BigDecimal.valueOf(13))
        );

        verify(registerRepository).save(argThat(reg -> {
            assertEquals(1L, reg.getId());
            assertEquals(BigDecimal.valueOf(13), reg.getBalance());
            return true;
        }));
    }

}
