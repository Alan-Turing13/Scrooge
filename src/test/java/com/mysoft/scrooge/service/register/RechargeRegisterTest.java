package com.mysoft.scrooge.service.register;

import com.mysoft.scrooge.model.Register;
import com.mysoft.scrooge.service.InvalidMonetaryValueException;
import com.mysoft.scrooge.service.InvalidRegisterOperationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

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

    @Test
    public void givenARegisterWith7_WhenRechargedWith13pt350_ThenBalanceOf20pt25IsPersisted() {

        var theRegister = new Register(1,"Test register");
        theRegister.setBalance(BigDecimal.valueOf(7));
        doReturn(Optional.of(theRegister)).when(registerRepository).findById(1L);

        assertDoesNotThrow( () ->
                registerService.recharge(1L, new BigDecimal("13.350"))
        );

        verify(registerRepository).save(argThat(reg -> {
            assertEquals(1L, reg.getId());
            assertEquals(new BigDecimal("20.35"), reg.getBalance());
            return true;
        }));
    }

    @Test
    public void givenARegister_WhenRechargedWith0_ThenExceptionIsThrown() {

        var theRegister = new Register(1,"Test register");
        theRegister.setBalance(BigDecimal.valueOf(7));
        doReturn(Optional.of(theRegister)).when(registerRepository).findById(1L);

        assertThrows(InvalidMonetaryValueException.class, () ->
                registerService.recharge(1L, BigDecimal.valueOf(0))
        );

        verify(registerRepository, never()).save(any());
    }

    @Test
    public void givenARegister_WhenRechargedWithNegativeValue_ThenExceptionIsThrown() {

        var theRegister = new Register(1,"Test register");
        theRegister.setBalance(BigDecimal.valueOf(7));
        doReturn(Optional.of(theRegister)).when(registerRepository).findById(1L);

        assertThrows(InvalidMonetaryValueException.class, () ->
                registerService.recharge(1L, BigDecimal.valueOf(-3))
        );

        verify(registerRepository, never()).save(any());
    }

    @Test
    public void givenARegister_WhenRechargedWithInvalidMonetaryAmount_ThenExceptionIsThrown() {

        var theRegister = new Register(1,"Test register");
        theRegister.setBalance(BigDecimal.valueOf(7));
        doReturn(Optional.of(theRegister)).when(registerRepository).findById(1L);

        assertThrows(InvalidMonetaryValueException.class, () ->
                registerService.recharge(1L, new BigDecimal("13.213"))
        );

        verify(registerRepository, never()).save(any());
    }
}
