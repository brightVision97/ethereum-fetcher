package com.rachev.ethereumfetcher.controller;


import com.rachev.ethereumfetcher.model.auth.AuthRequest;
import com.rachev.ethereumfetcher.model.auth.RefreshTokenRequest;
import com.rachev.ethereumfetcher.service.base.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/lime")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

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
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest request) {
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
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request, Principal principal) {
        final var username = ((UserDetails) (((UsernamePasswordAuthenticationToken) principal)).getPrincipal()).getUsername();
        return ResponseEntity.ok(authenticationService.refreshToken(request, username));
    }
}