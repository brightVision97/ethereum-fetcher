package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.transaction.TransactionReceiptDto;
import com.rachev.ethereumfetcher.model.transaction.UnifiedTransactionDto;

public interface EthereumNodeRequestSender {

    UnifiedTransactionDto getTransactionByHash(final String hash);

    Integer getLogsCountByBlockHash(final String blockHash);

    TransactionReceiptDto getTransactionReceipt(final String transactionHash);
}
