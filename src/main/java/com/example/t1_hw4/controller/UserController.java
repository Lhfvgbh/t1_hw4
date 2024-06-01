package com.example.t1_hw4.controller;

import com.example.t1_hw4.model.response.UserResponse;
import com.example.t1_hw4.service.SystemUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для работы с сущностями Пользователь
 * Только для роли Администратор
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final SystemUserDetailsService userService;

    /**
     * Получить список всех пользователей
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    /**
     * Получить пользователя по id
     */
    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public UserResponse findById(@PathVariable Long userId) {
        return userService.findById(userId);
    }


    /**
     * Удалить пользователя по id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}