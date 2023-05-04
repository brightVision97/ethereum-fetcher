package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.transaction.TransactionReceiptDto;
import com.rachev.ethereumfetcher.model.transaction.UnifiedTransactionDto;
import jakarta.annotation.Nullable;

public interface EthereumNodeRequestSender {

    UnifiedTransactionDto getTransactionByHash(final String hash, @Nullable String infuraNetwork);

    Integer getLogsCountByBlockHash(final String blockHash);

    TransactionReceiptDto getTransactionReceipt(final String transactionHash);
}
