package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.auth.AuthRequest;
import com.rachev.ethereumfetcher.model.auth.AuthResponse;
import com.rachev.ethereumfetcher.model.auth.RefreshTokenRequest;

public interface AuthenticationService {

    AuthResponse authenticate(AuthRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);
}
