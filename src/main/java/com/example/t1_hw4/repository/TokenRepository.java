package com.example.t1_hw4.repository;

import com.example.t1_hw4.domain.SystemUserDetails;
import com.example.t1_hw4.domain.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findTopByUserDetailsOrderByExpiredAtDesc(SystemUserDetails userDetails);
}