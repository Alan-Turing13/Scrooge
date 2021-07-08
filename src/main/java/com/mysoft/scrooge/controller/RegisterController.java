package com.mysoft.scrooge.controller;

import com.mysoft.scrooge.service.InvalidMonetaryValueException;
import com.mysoft.scrooge.service.InvalidRegisterOperationException;
import com.mysoft.scrooge.service.RegisterNotFoundException;
import com.mysoft.scrooge.service.RegisterService;
import com.mysoft.scrooge.service.dto.RegisterBalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RegisterController {

    private final RegisterService registerService;

    //TODO: extract to bean
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/register/get_balance")
    public List<RegisterBalanceDto> getBalance() {
        var registers = registerService.getBalance();
        return registers.stream().map(r -> modelMapper.map(r, RegisterBalanceDto.class)).collect(Collectors.toList());
    }

    //TODO: consider some return value (new balance?)
    @RequestMapping(method = RequestMethod.POST, value = "/api/register/{id}/recharge")
    public void recharge(@PathVariable long id, BigDecimal amount) throws RegisterNotFoundException, InvalidRegisterOperationException, InvalidMonetaryValueException {
        registerService.recharge(id, amount);
    }

    //TODO: consider some return value (updated balances?)
    @RequestMapping(method = RequestMethod.POST, value = "/api/register/transfer/{sourceId}/{targetId}")
    public void transfer(@PathVariable long sourceId, @PathVariable long targetId, BigDecimal amount) throws RegisterNotFoundException, InvalidRegisterOperationException, InvalidMonetaryValueException {
        registerService.transfer(sourceId, targetId, amount);
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid operation")
    @ExceptionHandler({InvalidRegisterOperationException.class, InvalidMonetaryValueException.class})
    public void badRequest() {
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Register not found")
    @ExceptionHandler({RegisterNotFoundException.class})
    public void notFound() {
    }
}
