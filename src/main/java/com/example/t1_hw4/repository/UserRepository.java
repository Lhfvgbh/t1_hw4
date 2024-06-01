package com.example.t1_hw4.repository;

import com.example.t1_hw4.domain.SystemUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SystemUserDetails, Long> {

    Optional<SystemUserDetails> findByUsername(String username);
    Optional<SystemUserDetails> findByEmail(String email);
}