package com.example.t1_hw4.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Модель запроса авторизации пользователя
 */
@Data
@Accessors(chain = true)
public class LoginRequest {
    private String login;
    private String password;
}
