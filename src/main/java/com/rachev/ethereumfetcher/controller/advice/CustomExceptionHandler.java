package com.rachev.ethereumfetcher.controller.advice;

import com.rachev.ethereumfetcher.model.ApiError;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, IllegalArgumentException.class})
    protected ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        final ApiError apiError = ApiError.builder()
                .message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NotNull MissingServletRequestParameterException ex,
                                                                          @NotNull HttpHeaders headers,
                                                                          @NotNull HttpStatusCode status,
                                                                          @NotNull WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        final ApiError apiError = ApiError.builder()
                .message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(@NotNull MissingPathVariableException ex,
                                                               @NotNull HttpHeaders headers,
                                                               @NotNull HttpStatusCode status,
                                                               @NotNull WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        final ApiError apiError = ApiError.builder()
                .message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        final ApiError apiError = ApiError.builder()
                .message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}