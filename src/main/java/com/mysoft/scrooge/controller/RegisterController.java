package com.mysoft.scrooge.controller;

import com.mysoft.scrooge.service.RegisterService;
import com.mysoft.scrooge.service.dto.RegisterBalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

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

    @RequestMapping(method = RequestMethod.GET, value = "/api/ping")
    public String ping() {
        return registerService.ping();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/register/get_balance")
    public List<RegisterBalanceDto> getBalance() {
        var registers = registerService.getBalance();
        return registers.stream().map(r -> modelMapper.map(r, RegisterBalanceDto.class)).collect(Collectors.toList());
    }
}
