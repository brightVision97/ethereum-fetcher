package com.rachev.ethereumfetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ApiError(

        @JsonIgnore
        HttpStatus httpStatus,

        int status,

        String message,

        LocalDateTime timestamp,

        String description
) {
}
