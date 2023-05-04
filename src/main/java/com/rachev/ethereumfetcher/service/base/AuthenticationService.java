package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.jwt.JwtRequest;
import com.rachev.ethereumfetcher.model.jwt.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    JwtResponse authenticate(JwtRequest request);

    JwtResponse refreshToken(HttpServletRequest request);
}
