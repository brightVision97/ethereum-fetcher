package com.rachev.ethereumfetcher.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class TransactionReceiptDto {

    private String blockHash;

    private String blockNumber;

    private String contractAddress;

    private String cumulativeGasUsed;

    private String effectiveGasPrice;

    private String from;

    private String gasUsed;

    private List<LogDto> logs;

    private String logsBloom;

    private String root;

    private String status;

    private String to;

    private String transactionHash;

    private String transactionIndex;

    private String type;
}
