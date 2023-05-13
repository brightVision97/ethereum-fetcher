package com.rachev.ethereumfetcher.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "transaction", indexes = @Index(columnList = "transactionHash, blockHash"))
@Data
public class Transaction {

    @Id
    @SequenceGenerator(name = "transactionSeqGen", sequenceName = "transactionSeq", initialValue = 5, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionSeqGen")
    private Long id;

    @Column(nullable = false, unique = true)
    private String transactionHash;

    private String transactionStatus;

    @Column(nullable = false)
    private String blockHash;

    @Column(nullable = false)
    private String blockNumber;

    @Column(name = "_from", nullable = false)
    private String from;

    @Column(name = "_to")
    private String to;

    private String contractAddress;

    private Integer logsCount;

    @Column(columnDefinition = "text", nullable = false)
    private String input;

    @Column(nullable = false)
    private String value;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "transactions")
    private List<User> users = new ArrayList<>();
}
