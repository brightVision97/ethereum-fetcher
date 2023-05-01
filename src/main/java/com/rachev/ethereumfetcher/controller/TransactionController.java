package com.rachev.ethereumfetcher.controller;

import com.rachev.ethereumfetcher.service.base.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lime")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/eth/{rlphex}")
    public ResponseEntity<?> getTransactionsByRlpHex(@PathVariable String rlphex) {
        return ResponseEntity.ok(transactionService.getTransactionsByHashes(rlphex));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
