package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.model.eth.*;
import com.rachev.ethereumfetcher.model.transaction.TransactionReceiptDto;
import com.rachev.ethereumfetcher.model.transaction.UnifiedTransactionDto;
import com.rachev.ethereumfetcher.service.base.EthereumNodeRequestSender;
import com.rachev.ethereumfetcher.service.base.HttpService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public final class EthereumNodeRequestSenderImpl implements EthereumNodeRequestSender, HttpService {

    private static final String GET_TRANSACTION_BY_HASH_METHOD = "eth_getTransactionByHash";
    private static final String GET_LOGS_METHOD = "eth_getLogs";
    private static final String GET_TRANSACTION_RECEIPT_METHOD = "eth_getTransactionReceipt";
    private static final String BLOCK_HASH_PARAM = "blockHash";
    private static final String TOPICS_PARAM = "topics";

    @Value("${eth.infura.goerli.node-url}")
    private String ethereumNodeUrl;

    private WebClient webClient;

    @PostConstruct
    private void webClientInit() {
        webClient = WebClient.builder()
                .baseUrl(ethereumNodeUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(WEB_CLIENT_BUFFER_SIZE))
                        .build())
                .build();
    }

    @Override
    public UnifiedTransactionDto getTransactionByHash(final String hash) {
        final var request = new InfuraTransactionByHashRequestDto();
        request.setMethod(GET_TRANSACTION_BY_HASH_METHOD);
        request.setParams(List.of(hash));
        var response = sendPostRequestWithBody(
                webClient, InfuraTransactionByHashResponseDto.class, request, InfuraTransactionByHashRequestDto.class
        );
        final var transaction = response.getResult();
        final var receipt = getTransactionReceipt(hash);
        final Integer logsCount = getLogsCountByBlockHash(transaction.getBlockHash());
        return UnifiedTransactionDto.builder()
                .transactionHash(transaction.getHash())
                .transactionStatus(receipt.getStatus())
                .blockHash(transaction.getBlockHash())
                .blockNumber(transaction.getBlockNumber())
                .from(transaction.getFrom())
                .to(transaction.getTo() == null ? receipt.getTo() : transaction.getTo())
                .contractAddress(receipt.getContractAddress())
                .logsCount(logsCount)
                .input(transaction.getInput())
                .value(transaction.getValue())
                .build();
    }

    @Override
    public Integer getLogsCountByBlockHash(String blockHash) {
        if (StringUtils.isBlank(blockHash)) {
            return null;
        }
        final var request = new InfuraGetLogsRequestDto();
        request.setMethod(GET_LOGS_METHOD);
        request.setParams(List.of(Map.of(BLOCK_HASH_PARAM, blockHash, TOPICS_PARAM, List.of())));
        var response = sendPostRequestWithBody(webClient, InfuraGetLogsResponseDto.class, request, InfuraGetLogsRequestDto.class);
        return CollectionUtils.isEmpty(response.getResult()) ? 0 : response.getResult().size();
    }

    @Override
    public TransactionReceiptDto getTransactionReceipt(String transactionHash) {
        final var request = new InfuraTransactionByHashRequestDto();
        request.setMethod(GET_TRANSACTION_RECEIPT_METHOD);
        request.setParams(List.of(transactionHash));
        var response = sendPostRequestWithBody(
                webClient, InfuraTransactionReceiptResponseDto.class, request, InfuraTransactionByHashRequestDto.class
        );
        return response.getResult() == null ? new TransactionReceiptDto() : response.getResult();
    }
}
