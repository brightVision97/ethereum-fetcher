package com.rachev.ethereumfetcher.controller;


import com.rachev.ethereumfetcher.model.jwt.JwtRequest;
import com.rachev.ethereumfetcher.service.base.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lime")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            method = "POST",
            description = "Retrieve a JWT token by providing username and password credentials",
            summary = "Obtain a pair of access and refresh tokens",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticfationToken(@RequestBody JwtRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Operation(
            method = "POST",
            description = "Perfom refreshing of the jwt token",
            summary = "Perform token refresh",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}