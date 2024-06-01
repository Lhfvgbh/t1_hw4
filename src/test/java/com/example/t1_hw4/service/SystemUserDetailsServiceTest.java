package com.example.t1_hw4.service;

import com.example.t1_hw4.domain.SystemUserDetails;
import com.example.t1_hw4.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SystemUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SystemUserDetailsService systemUserDetailsService;

    private static SystemUserDetails testUser;

    private static final String TEST_STRING = "test";

    @BeforeAll
    public static void initUser() {
        testUser = new SystemUserDetails()
                .setUsername(TEST_STRING)
                .setEmail(TEST_STRING)
                .setPassword(TEST_STRING);
    }

    @Test
    public void should_success_when_loadUserByUsername() {
        when(userRepository.findByUsername(TEST_STRING)).thenReturn(Optional.of(testUser));

        var result = systemUserDetailsService.loadUserByUsername(TEST_STRING);

        verify(userRepository).findByUsername(TEST_STRING);
        assertSame(testUser, result);
    }

    @Test
    public void should_fail_when_loadUserByUsername() {
        when(userRepository.findByUsername(TEST_STRING)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> systemUserDetailsService.loadUserByUsername(TEST_STRING));
    }

    @Test
    public void should_success_create() {
        when(userRepository.findByUsername(TEST_STRING)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(TEST_STRING)).thenReturn(Optional.empty());

        systemUserDetailsService.createUser(testUser);

        verify(userRepository).save(testUser);
    }

    @Test
    public void should_fail_when_usernameExists() {
        when(userRepository.findByUsername(TEST_STRING)).thenReturn(Optional.of(new SystemUserDetails()));

        assertThrows(RuntimeException.class, () -> systemUserDetailsService.createUser(testUser));
    }

    @Test
    public void should_fail_when_emailExists() {
        when(userRepository.findByUsername(TEST_STRING)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(TEST_STRING)).thenReturn(Optional.of(new SystemUserDetails()));

        assertThrows(RuntimeException.class, () -> systemUserDetailsService.createUser(testUser));
    }

}