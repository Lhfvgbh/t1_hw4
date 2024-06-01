package com.example.t1_hw4.service;

import com.example.t1_hw4.domain.SystemUserDetails;
import com.example.t1_hw4.model.response.UserResponse;
import com.example.t1_hw4.repository.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с пользователями
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public SystemUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    /**
     * Создать пользователя
     *
     * @param userDetails Данные пользователя для обновления
     */
    @Nonnull
    @Transactional
    public void createUser(@Nonnull SystemUserDetails userDetails) {

        var existingUser = userRepository.findByUsername(userDetails.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Пользователь с таким login уже существует");
        }

        existingUser = userRepository.findByEmail(userDetails.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        userRepository.save(userDetails);
    }

    /**
     * Получить список пользователей
     *
     * @return список пользователей
     */
    @Nonnull
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::buildUserResponse)
                .toList();
    }

    /**
     * Получить пользователя по идентификатору
     *
     * @param userId Идентификатор пользователеля
     * @return пользователь
     */
    @Nonnull
    @Transactional(readOnly = true)
    public UserResponse findById(@Nonnull Long userId) {
        return userRepository.findById(userId)
                .map(this::buildUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " is not found"));
    }

    /**
     * Удалить пользователя по идентификатору
     *
     * @param userId Идентификатор пользователеля
     */
    @Transactional
    public void delete(@Nonnull Long userId) {
        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            log.warn("delete: User not found with userId = " + userId);
            return;
        }
        userRepository.deleteById(userId);
    }

    @Nonnull
    private UserResponse buildUserResponse(@Nonnull SystemUserDetails userDetails) {
        return new UserResponse()
                .setId(userDetails.getId())
                .setLogin(userDetails.getUsername())
                .setEmail(userDetails.getEmail());
    }
}