package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.transaction.TransactionsDto;
import com.rachev.ethereumfetcher.model.transaction.UnifiedTransactionDto;

import java.util.List;

public interface TransactionService {

    List<UnifiedTransactionDto> getTransactionsByHashes(final String rlpHex, String requesterUsername);

    TransactionsDto getAllTransactions();

    TransactionsDto getMyTransactions(String username);
}
