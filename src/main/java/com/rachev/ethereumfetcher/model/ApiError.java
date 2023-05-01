package com.rachev.ethereumfetcher.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        String message,

        LocalDateTime timestamp
) {
}

