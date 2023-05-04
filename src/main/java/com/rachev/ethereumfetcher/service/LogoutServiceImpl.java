package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {

    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return;
        }
        final String jwt = authHeader.substring(BEARER_PREFIX.length());
        tokenRepository.findByToken(jwt)
                .ifPresent(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                    tokenRepository.save(token);
                    SecurityContextHolder.clearContext();
                });
    }
}
