package com.example.t1_hw4.service;

import com.example.t1_hw4.domain.SystemUserDetails;
import com.example.t1_hw4.domain.TokenEntity;
import com.example.t1_hw4.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    @Test
    public void should_success_save() {
        var tokenEntity = new TokenEntity();

        tokenService.saveToken(tokenEntity);

        verify(tokenRepository, times(1)).save(tokenEntity);
    }

    @Test
    public void testGetLastTokenForUser() {
        var user = new SystemUserDetails()
                .setUsername("test")
                .setEmail("test")
                .setPassword("test");
        var tokenEntity = new TokenEntity();
        when(tokenRepository.findTopByUserDetailsOrderByExpiredAtDesc(user))
                .thenReturn(Optional.of(tokenEntity));

        var result = tokenService.getLastTokenForUser(user);

        verify(tokenRepository, times(1))
                .findTopByUserDetailsOrderByExpiredAtDesc(user);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(tokenEntity);
    }
}