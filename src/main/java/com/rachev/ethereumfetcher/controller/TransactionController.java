package com.rachev.ethereumfetcher.controller;

import com.rachev.ethereumfetcher.service.base.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(
            method = "GET",
            description = "Get endpoint for all transactions by using encoded hashes",
            summary = "Get transactions by given RLP encoded list of transaction hashes",
            parameters = @Parameter(
                    name = "rlphex",
                    description = "A path variable that is the encoded RLP hashes"
            ),
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
                            description = "Internal server error",
                            responseCode = "500",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/eth/{rlphex}")
    public ResponseEntity<?> getTransactionsByRlpHex(@PathVariable String rlphex,
                                                     @RequestParam(required = false) String network,
                                                     Principal principal) {
        final var username = ((UserDetails) (((UsernamePasswordAuthenticationToken) principal)).getPrincipal()).getUsername();
        return ResponseEntity.ok(transactionService.getTransactionsByHashes(rlphex, network, username));
    }

    @Operation(
            method = "GET",
            description = "Get endpoint for all transactions stored in the database",
            summary = "Get all stored transactions",
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
                            description = "Internal server error",
                            responseCode = "500",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @Operation(
            method = "GET",
            description = "Get endpoint for all transactions associated with the current authenticated principal",
            summary = "Get all stored transactions for the current authenticated principal",
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
                            description = "Internal server error",
                            responseCode = "500",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/my")
    public ResponseEntity<?> getMyTransactions(Principal principal) {
        final var username = ((UserDetails) (((UsernamePasswordAuthenticationToken) principal)).getPrincipal()).getUsername();
        return ResponseEntity.ok(transactionService.getMyTransactions(username));
    }
}
