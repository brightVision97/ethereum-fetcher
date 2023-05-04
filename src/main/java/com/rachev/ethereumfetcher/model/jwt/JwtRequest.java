package com.rachev.ethereumfetcher.model.jwt;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;

    private String password;
}
