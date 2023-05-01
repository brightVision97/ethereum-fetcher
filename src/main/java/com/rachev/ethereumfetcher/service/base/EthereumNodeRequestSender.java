package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.model.TransactionDto;

public interface EthereumNodeRequestSender {

    TransactionDto getTransactionByHashFromEthereumNode(final String hash);
}
