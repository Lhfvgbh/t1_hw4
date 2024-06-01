package com.example.t1_hw4.model.response;

import lombok.Data;

/**
 * Модель ответа с ошибкой
 */
@Data
public class ExceptionResponse {
    private final String massage;
}