package com.mysoft.scrooge.service.register;

import com.mysoft.scrooge.persistence.RegisterRepository;
import com.mysoft.scrooge.service.RegisterService;
import com.mysoft.scrooge.service.RegisterServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegisterTestBase {

    private AutoCloseable mocks;

    protected RegisterService registerService;

    @Mock
    protected RegisterRepository registerRepository;

    @BeforeEach
    public void setUpTest() {
        mocks = MockitoAnnotations.openMocks(this);
        registerService = new RegisterServiceImpl(registerRepository);
    }

    @AfterEach
    public void tearDownTest() throws Exception {
        mocks.close();
    }

}
