package com.example.t1_hw4.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Модель запроса регистрации пользователя
 */
@Data
@Accessors(chain = true)
public class RegisterRequest {
    private String login;
    private String email;
    private String password;
}