package com.rachev.ethereumfetcher.model.transaction;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedTransactionDto {

    private String transactionHash;

    private String transactionStatus;

    private String blockHash;

    private String blockNumber;

    private String from;

    @Nullable
    private String to;

    @Nullable
    private String contractAddress;

    private Integer logsCount;

    private String input;

    private String value;
}
