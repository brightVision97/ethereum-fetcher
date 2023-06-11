package com.rachev.ethereumfetcher.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final TokenRepository tokenRepository;


    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final var user = userService.getUserByUsername(request.getUsername());
        final var token = jwtTokenUtil.generateToken(user);
        revokeAllUserTokens(user);
        final var refreshToken = jwtTokenUtil.generateRefreshToken(user);
        saveUserToken(user, token);
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
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

    @Transactional
    private void revokeAllUserTokens(User user) {
        tokenRepository.findAllValidTokenByUser(user.getUsername())
                .forEach(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                    tokenRepository.save(token);
                });
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request, String username) {
        var user = userService.getUserByUsername(username);
        var requestRefreshToken = request.getRefreshToken();
        return Optional.ofNullable(requestRefreshToken)
                .filter(rt -> jwtTokenUtil.isTokenValid(rt, user))
                .map(toke -> AuthResponse.builder()
                        .accessToken(jwtTokenUtil.generateToken(user))
                        .refreshToken(jwtTokenUtil.generateRefreshToken(user))
                        .build())
                .orElseThrow(() -> new RefreshTokenException("Refresh token is not in database!"));
    }
}
