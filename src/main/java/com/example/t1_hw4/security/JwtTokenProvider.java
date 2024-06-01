package com.example.t1_hw4.security;

import com.example.t1_hw4.domain.SystemUserDetails;
import com.example.t1_hw4.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

/**
 * Сервис работы с jwt-токенами
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private static SecretKey key;

    private final TokenService tokenService;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    }

    /**
     * Метод генерации jwt-токена пользователя
     *
     * @param userDetails Пользователь
     * @return токен
     */
    @Nonnull
    public String generateToken(@Nonnull SystemUserDetails userDetails) {
        var issuedDate = new Date();
        var expirationDate = new Date(issuedDate.getTime() + jwtExpiration);

        var claims = new HashMap<String, Object>();
        claims.put("login", userDetails.getUsername());
        claims.put("roles", userDetails.getRoles());
        claims.put("email", userDetails.getEmail());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(issuedDate)
                .expiration(expirationDate)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Возвращает флаг валидности токена
     *
     * @param token       Токен
     * @param userDetails Пользователь
     * @return флаг валидности
     */
    public boolean isTokenValid(@Nonnull String token,
                                @Nonnull SystemUserDetails userDetails) {
        return tokenService.getLastTokenForUser(userDetails)
                .map(lastSavedToken -> lastSavedToken.getToken().equals(token)
                        && !lastSavedToken.isExpired())
                .orElse(false);
    }

    /**
     * Возвращает логин, расшифрованный из токена
     *
     * @param token Токен
     * @return логин пользователя
     */
    @Nonnull
    public String extractLogin(@Nonnull String token) {
        return extractAllClaims(token).get("login", String.class);
    }

    /**
     * Возвращает время протухания токена, расшифрованное из токена
     *
     * @param token Токен
     * @return время протухания токена
     */
    @Nonnull
    public Date extractExpirationDate(@Nonnull String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * Возвращает условия токена, расшифрованное из токена
     *
     * @param token Токен
     * @return условия токена
     */
    @Nonnull
    private Claims extractAllClaims(@Nonnull String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}