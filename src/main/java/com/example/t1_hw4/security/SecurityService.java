package com.example.t1_hw4.security;

import com.example.t1_hw4.domain.SystemUserDetails;
import com.example.t1_hw4.domain.TokenEntity;
import com.example.t1_hw4.domain.UserRole;
import com.example.t1_hw4.model.request.LoginRequest;
import com.example.t1_hw4.model.request.RegisterRequest;
import com.example.t1_hw4.service.SystemUserDetailsService;
import com.example.t1_hw4.service.TokenService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Сервис работы с пользовательской авторизацией
 */
@Service
@RequiredArgsConstructor
public class SecurityService {

    private final SystemUserDetailsService systemUserDetailsService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Выполняет регистрацию пользователя с дальнейшей авторизацией
     *
     * @param request Запрос регистрации
     * @return токен
     */
    @Nonnull
    @Transactional
    public String registerUser(@Nonnull RegisterRequest request) {

        var userDetails = new SystemUserDetails()
                .setUsername(request.getLogin())
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRoles(Set.of(UserRole.USER));

        systemUserDetailsService.createUser(userDetails);

        return createAndSaveNewTokenForUser(userDetails);
    }

    /**
     * Выполняет авторизацию пользователя
     *
     * @param request Запрос авторизации
     * @return токен
     */
    @Nonnull
    @Transactional
    public String loginUser(@Nonnull LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

        var user = systemUserDetailsService.loadUserByUsername(request.getLogin());

        return getActualTokenForUser(user);
    }

    /**
     * Создает токен пользователя
     *
     * @param userDetails Пользователь
     * @return токен
     */
    @Nonnull
    private String createAndSaveNewTokenForUser(@Nonnull SystemUserDetails userDetails) {
        var jwt = jwtTokenProvider.generateToken(userDetails);

        tokenService.saveToken(
                new TokenEntity()
                        .setUserDetails(userDetails)
                        .setToken(jwt)
                        .setExpiredAt(jwtTokenProvider.extractExpirationDate(jwt)));
        return jwt;
    }

    /**
     * Возвращает актуальный токен пользователя.
     * Если существующий токен не протух, то вернется он;
     * Если токен протух или не найден, то будет создан новый.
     *
     * @param userDetails Пользователь
     * @return токен
     */
    @Nonnull
    private String getActualTokenForUser(@Nonnull SystemUserDetails userDetails) {
        var lastTokenForUser = tokenService.getLastTokenForUser(userDetails);

        return lastTokenForUser.isEmpty() || lastTokenForUser.get().isExpired()
                ? createAndSaveNewTokenForUser(userDetails)
                : lastTokenForUser.get().getToken();
    }
}
