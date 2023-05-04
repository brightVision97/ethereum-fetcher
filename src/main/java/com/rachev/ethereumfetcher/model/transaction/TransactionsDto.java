package com.rachev.ethereumfetcher.model.transaction;

import lombok.Builder;

import java.util.Collection;

@Builder
public record TransactionsDto(

        Collection<UnifiedTransactionDto> transactions
) {
}
