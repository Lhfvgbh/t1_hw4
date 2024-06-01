package com.example.t1_hw4.service;

import com.example.t1_hw4.domain.SystemUserDetails;
import com.example.t1_hw4.domain.TokenEntity;
import com.example.t1_hw4.repository.TokenRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с пользовательскими токенами
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    /**
     * Сохранить токен
     *
     * @param tokenEntity Токен
     */
    @Transactional
    public void saveToken(@Nonnull TokenEntity tokenEntity) {
        tokenRepository.save(tokenEntity);
    }

    /**
     * Получить токен для пользователя
     *
     * @param userDetails Пользователь
     * @return токен
     */
    @Nonnull
    @Transactional
    public Optional<TokenEntity> getLastTokenForUser(@Nonnull SystemUserDetails userDetails) {
        return tokenRepository.findTopByUserDetailsOrderByExpiredAtDesc(userDetails);
    }
}