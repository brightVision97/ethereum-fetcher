package com.rachev.ethereumfetcher.model;

import jakarta.annotation.Nullable;

public record InfuraGoerliTransactionByHashResponseDto(

        String jsonrpc,

        Long id,

        @Nullable
        TransactionDto result
) {
}
