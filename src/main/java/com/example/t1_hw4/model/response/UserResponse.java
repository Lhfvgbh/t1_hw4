package com.example.t1_hw4.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Модель ответа на запрос данных пользователя
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Integer id;
    private String login;
    private String email;
}
