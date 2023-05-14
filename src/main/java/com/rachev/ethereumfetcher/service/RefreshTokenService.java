package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.RefreshToken;
import com.rachev.ethereumfetcher.exception.RefreshTokenException;
import com.rachev.ethereumfetcher.repository.RefreshTokenRepository;
import com.rachev.ethereumfetcher.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    @Value("${security.jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        var refreshToken = RefreshToken.builder()
                .user(userService.getUserById(userId))
                .expirationDate(Instant.now().plusMillis(refreshExpiration))
                .token(UUID.randomUUID().toString())
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpirationDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.findAllByUserId(userId).forEach(refreshTokenRepository::delete);
    }
}
