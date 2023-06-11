package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.transaction.TransactionsDto;
import com.rachev.ethereumfetcher.model.transaction.UnifiedTransactionDto;
import jakarta.annotation.Nullable;

import java.util.List;

public interface TransactionService {

    List<UnifiedTransactionDto> getTransactionsByHashes(final String rlpHex, @Nullable String networkSwitch, String requesterUsername);

    TransactionsDto getAllTransactions();

    TransactionsDto getMyTransactions(String username);
}
