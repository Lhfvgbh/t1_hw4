package com.example.t1_hw4.controller;

import com.example.t1_hw4.model.request.LoginRequest;
import com.example.t1_hw4.model.request.RegisterRequest;
import com.example.t1_hw4.security.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private AuthController authController;

    private final String SUCCESS_TOKEN = "successToken";

    @Test
    public void should_success_when_register() {
        var request = new RegisterRequest();
        when(securityService.registerUser(request)).thenReturn(SUCCESS_TOKEN);

        var responseEntity = authController.register(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUCCESS_TOKEN, responseEntity.getBody());
    }

    @Test
    public void should_success_when_login() {
        var request = new LoginRequest();
        when(securityService.loginUser(request)).thenReturn(SUCCESS_TOKEN);

        var responseEntity = authController.login(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUCCESS_TOKEN, responseEntity.getBody());
    }
}