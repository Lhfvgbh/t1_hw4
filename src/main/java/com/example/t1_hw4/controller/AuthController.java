package com.example.t1_hw4.controller;

import com.example.t1_hw4.model.request.LoginRequest;
import com.example.t1_hw4.model.request.RegisterRequest;
import com.example.t1_hw4.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для работы с аутентикацией и авторизацией пользователя
 * Аутентификация проверяет личность пользователя,
 * чтобы убедиться, что он является авторизованным пользователем организации.
 * Авторизация определяет уровень доступа пользователя к ресурсам организации
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    /**
     * Выполнить регистрацию нового пользователя
     */
    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(securityService.registerUser(request));
    }

    /**
     * Выполнить авторизацию пользователя
     */
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(securityService.loginUser(request));
    }

    /**
     * Сбросить авторизацию пользователя
     */
    @PostMapping(value = "/logout", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        var securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
