package com.rachev.ethereumfetcher.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

@Data
public class LogDto {

    @JsonIgnore
    private Long id;

    private String address;

    private String blockHash;

    private String blockNumber;

    private String data;

    private String logIndex;

    private Boolean removed;

    @JsonIgnore
    @Transient
    private List<String> topics;

    private String transactionHash;

    private String transactionIndex;
}
