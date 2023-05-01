package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.TransactionsDto;

public interface TransactionService {

    TransactionsDto getTransactionsByHashes(final String rlpHex);

    TransactionsDto getAllTransactions();
}
