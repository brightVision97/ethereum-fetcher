package com.rachev.ethereumfetcher.model.transaction;

import lombok.Builder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Builder
public record TransactionsDto(

        Collection<UnifiedTransactionDto> transactions
) {
}
