package com.mysoft.scrooge.service;

import com.mysoft.scrooge.model.Register;
import com.mysoft.scrooge.persistence.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepo;

    @Autowired
    public RegisterServiceImpl(RegisterRepository registerRepo) {
        this.registerRepo = registerRepo;
    }

    @Override
    public String ping() {
        return "PONG!";
    }

    /*
    TODO: specify ordering of returned registers
     */
    @Override
    public List<Register> getBalance() {
        Iterable<Register> iterable =  registerRepo.findAll();
        List<Register> allRegisters = new ArrayList<>();
        iterable.forEach(allRegisters::add);
        return allRegisters;
    }

    @Override
    public void recharge(long registerId, BigDecimal amount) throws RegisterNotFoundException, InvalidMonetaryValueException {

        amount = amount.stripTrailingZeros();

        validateAmount(amount);

        Register register = registerRepo.findById(registerId).orElseThrow(() -> registerNotFound(registerId));

        BigDecimal newAmount = register.getBalance().add(amount);
        register.setBalance(newAmount);
        registerRepo.save(register);
    }

    private void validateAmount(BigDecimal amount) throws InvalidMonetaryValueException {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidMonetaryValueException(amount, "Operation amount has to be greater than 0");
        }

        if(amount.scale() > 2) {
            throw new InvalidMonetaryValueException(amount, String.format("Invalid monetary value: %s", amount));
        }
    }

    @Override
    public void transfer(long sourceRegisterId, long destinationRegisterId, BigDecimal amount) throws RegisterNotFoundException, InvalidMonetaryValueException, InvalidRegisterOperationException {

        amount = amount.stripTrailingZeros();
        validateAmount(amount);

        Register sourceRegister = registerRepo.findById(sourceRegisterId).orElseThrow(() -> registerNotFound(sourceRegisterId));
        BigDecimal reducedAmount = sourceRegister.getBalance().subtract(amount);

        if(reducedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRegisterOperationException(sourceRegisterId, "Not enough funds");
        }

        Register destinationRegister = registerRepo.findById(destinationRegisterId).orElseThrow(() -> registerNotFound(destinationRegisterId));
        BigDecimal increasedAmount = destinationRegister.getBalance().add(amount);

        sourceRegister.setBalance(reducedAmount);
        destinationRegister.setBalance(increasedAmount);

        registerRepo.saveAll(List.of(sourceRegister, destinationRegister));
    }

    private RegisterNotFoundException registerNotFound(long registerId) {
        return new RegisterNotFoundException(registerId);
    }
}
