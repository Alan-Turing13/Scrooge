package com.mysoft.scrooge.service.register;

import com.mysoft.scrooge.model.Register;
import com.mysoft.scrooge.service.InvalidMonetaryValueException;
import com.mysoft.scrooge.service.InvalidRegisterOperationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransferTest extends RegisterTestBase {

    @Test
    public void givenTwoRegisters_WhenTransferringAValidAmount_BalanceOfBothIsUpdated() {

        Register sourceRegister = new Register(1L, "Source");
        sourceRegister.setBalance(BigDecimal.valueOf(13));
        Register destinationRegister = new Register(2L, "Destination");

        doReturn(Optional.of(sourceRegister)).when(registerRepository).findById(1L);
        doReturn(Optional.of(destinationRegister)).when(registerRepository).findById(2L);

        assertDoesNotThrow(() ->
            registerService.transfer(1L, 2L, BigDecimal.valueOf(7))
        );

        verify(registerRepository, never()).save(any());
        verify(registerRepository).saveAll(argThat((regs -> assertRegs(regs, BigDecimal.valueOf(6), BigDecimal.valueOf(7)))));

    }

    @Test
    public void givenTwoRegisters_WhenZeroIsTransferred_ThenExceptionIsThrown() {

        Register sourceRegister = new Register(1L, "Source");
        sourceRegister.setBalance(BigDecimal.valueOf(13));
        Register destinationRegister = new Register(2L, "Destination");

        doReturn(Optional.of(sourceRegister)).when(registerRepository).findById(1L);
        doReturn(Optional.of(destinationRegister)).when(registerRepository).findById(2L);

        assertThrows(InvalidMonetaryValueException.class, () ->
                registerService.transfer(1L, 2L, BigDecimal.valueOf(0))
        );

        verify(registerRepository, never()).save(any());
        verify(registerRepository, never()).saveAll(any());
    }
    //TODO: test for invalid monetary value
    //TODO: test for zero tranfer
    //TODO: test for negative transfer

    private boolean assertRegs(Iterable<Register> actual, BigDecimal sourceBalance, BigDecimal destinationBalance) {
        List<Register> actualList = iterableToList(actual);
        assertEquals(2, actualList.size());


        actualList.sort(Comparator.comparing(Register::getId));
        Register actualSource = actualList.get(0);
        Register actualDestination = actualList.get(1);

        assertEquals(sourceBalance, actualSource.getBalance());
        assertEquals(destinationBalance, actualDestination.getBalance());

        return true;
    }

    private <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
