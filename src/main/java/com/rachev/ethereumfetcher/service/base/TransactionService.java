package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.transaction.TransactionsDto;
import jakarta.annotation.Nullable;

public interface TransactionService {

    TransactionsDto getTransactionsByHashes(final String rlpHex, @Nullable String networkSwitch, String requesterUsername);

    TransactionsDto getAllTransactions();

    TransactionsDto getMyTransactions(String username);
}
