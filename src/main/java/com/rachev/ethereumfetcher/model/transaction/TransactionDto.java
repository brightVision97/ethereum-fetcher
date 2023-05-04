package com.rachev.ethereumfetcher.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rachev.ethereumfetcher.model.user.UserDto;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

// this isn't a record because the model mapper couldn't work properly
@Data
public class TransactionDto {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private List<Object> accessList;

    private String blockHash;

    private String blockNumber;

    private String chainId;

    private String from;

    private String to;

    private String gas;

    private String gasPrice;

    private String hash;

    private String input;

    private String maxFeePerGas;

    private String maxPriorityFeePerGas;

    private String nonce;

    private String r;

    private String s;

    private String transactionIndex;

    private String type;

    private String v;

    private String value;

    @JsonIgnore
    @Transient
    private List<UserDto> users;

    private int logCount;
}
