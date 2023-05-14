package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.RefreshToken;
import com.rachev.ethereumfetcher.entity.Token;
import com.rachev.ethereumfetcher.entity.Token.TokenType;
import com.rachev.ethereumfetcher.entity.User;
import com.rachev.ethereumfetcher.exception.RefreshTokenException;
import com.rachev.ethereumfetcher.model.auth.AuthRequest;
import com.rachev.ethereumfetcher.model.auth.AuthResponse;
import com.rachev.ethereumfetcher.model.auth.RefreshTokenRequest;
import com.rachev.ethereumfetcher.repository.TokenRepository;
import com.rachev.ethereumfetcher.service.base.AuthenticationService;
import com.rachev.ethereumfetcher.service.base.UserService;
import com.rachev.ethereumfetcher.util.JwtTokenUtil;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final TokenRepository tokenRepository;

    private final RefreshTokenService refreshTokenService;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final var user = userService.getUserByUsername(request.getUsername());
        final var token = jwtTokenUtil.generateToken(user);
        refreshTokenService.deleteByUserId(user.getId());
        final var refreshToken = refreshTokenService.createRefreshToken(user.getId());
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUsername());
        if (Collections.isEmpty(validUserTokens)) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        var requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    var token = jwtTokenUtil.generateToken(user);
                    return AuthResponse.builder()
                            .accessToken(token)
                            .refreshToken(requestRefreshToken)
                            .build();
                })
                .orElseThrow(() -> new RefreshTokenException("Refresh token is not in database!"));
    }
}
