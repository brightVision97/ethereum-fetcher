package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.model.InfuraGoerliTransactionByHashRequestDto;
import com.rachev.ethereumfetcher.model.InfuraGoerliTransactionByHashResponseDto;
import com.rachev.ethereumfetcher.model.TransactionDto;
import com.rachev.ethereumfetcher.service.base.EthereumNodeRequestSender;
import com.rachev.ethereumfetcher.service.base.HttpService;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.List;

@Service
@Primary
@Qualifier("InfuraGoerli")
@RequiredArgsConstructor
@Slf4j
public class EthereumNodeRequestSenderImpl implements EthereumNodeRequestSender, HttpService {

    private static final String RPC_VERSION = "2.0";

    private static final String GET_TRANSACTION_BY_HASH_METHOD = "eth_getTransactionByHash";

    @Value("${eth.infura.goerli.node-url}")
    private String ethereumNodeUrl;

    private WebClient webClient;

    @PostConstruct
    private void webClientInit() {
        webClient = WebClient.builder()
                .baseUrl(ethereumNodeUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Override
    @Nullable
    public TransactionDto getTransactionByHashFromEthereumNode(final String hash) {
        final InfuraGoerliTransactionByHashRequestDto request = InfuraGoerliTransactionByHashRequestDto.builder()
                .jsonrpc(RPC_VERSION)
                .method(GET_TRANSACTION_BY_HASH_METHOD)
                .params(List.of(hash))
                .id(1L)
                .build();
        InfuraGoerliTransactionByHashResponseDto response = sendPostRequestWithBody(
                webClient, InfuraGoerliTransactionByHashResponseDto.class, request, InfuraGoerliTransactionByHashRequestDto.class
        );
        return response.result();
    }
}
