package com.rachev.ethereumfetcher.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "transaction", indexes = @Index(columnList = "hash"))
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "block_number")
    private String blockNumber;

    @Column(name = "chain_id")
    private String chainId;

    @Column(name = "_from", nullable = false)
    private String from;

    @Column(name = "_to")
    private String to;

    @Column(name = "gas", nullable = false)
    private String gas;

    @Column(name = "gas_price", nullable = false)
    private String gasPrice;

    @Column(name = "hash", nullable = false, unique = true)
    private String hash;

    @Column(name = "input", columnDefinition = "text", nullable = false)
    private String input;

    @Column(name = "max_fee_per_gass")
    private String maxFeePerGas;

    @Column(name = "max_priority_fee_per_gas")
    private String maxPriorityFeePerGas;

    @Column(name = "nonce", nullable = false)
    private String nonce;

    @Column(name = "r", nullable = false)
    private String r;

    @Column(name = "s", nullable = false)
    private String s;

    @Column(name = "transaction_index")
    private String transactionIndex;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "v", nullable = false)
    private String v;

    @Column(name = "value", nullable = false)
    private String value;
}
