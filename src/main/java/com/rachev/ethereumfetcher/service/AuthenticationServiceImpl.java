package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.Token;
import com.rachev.ethereumfetcher.entity.Token.TokenType;
import com.rachev.ethereumfetcher.entity.User;
import com.rachev.ethereumfetcher.model.jwt.JwtRequest;
import com.rachev.ethereumfetcher.model.jwt.JwtResponse;
import com.rachev.ethereumfetcher.repository.TokenRepository;
import com.rachev.ethereumfetcher.service.base.AuthenticationService;
import com.rachev.ethereumfetcher.service.base.UserService;
import com.rachev.ethereumfetcher.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final TokenRepository tokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse authenticate(JwtRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final var user = userService.getUserByUsername(request.getUsername());
        final var token = jwtTokenUtil.generateToken(user);
        final var refreshToken = jwtTokenUtil.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return JwtResponse.builder()
                .token(token)
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

    @SneakyThrows
    @Override
    public JwtResponse refreshToken(HttpServletRequest request) {
        final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtException("Bearer header missing");
        }
        final var refreshToken = authHeader.substring(7);
        final var username = jwtTokenUtil.extractUsername(refreshToken);
        if (username != null) {
            var user = userService.getUserByUsername(username);
            if (jwtTokenUtil.isTokenValid(refreshToken, user)) {
                var accessToken = jwtTokenUtil.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                return JwtResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        throw new MalformedJwtException("Error while refreshing token");
    }
}
