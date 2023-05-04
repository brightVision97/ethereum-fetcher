package com.rachev.ethereumfetcher.model.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

    private String token;

    private String refreshToken;
}
