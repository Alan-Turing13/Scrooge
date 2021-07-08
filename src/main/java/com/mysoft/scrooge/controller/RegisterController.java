package com.mysoft.scrooge.controller;

import com.mysoft.scrooge.service.InvalidMonetaryValueException;
import com.mysoft.scrooge.service.InvalidRegisterOperationException;
import com.mysoft.scrooge.service.RegisterNotFoundException;
import com.mysoft.scrooge.service.RegisterService;
import com.mysoft.scrooge.service.dto.RegisterBalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
}
