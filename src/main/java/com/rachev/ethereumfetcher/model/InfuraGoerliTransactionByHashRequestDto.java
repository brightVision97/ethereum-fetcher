package com.rachev.ethereumfetcher.model;

import lombok.Builder;

import java.util.List;

@Builder
public record InfuraGoerliTransactionByHashRequestDto(

        String jsonrpc,

        String method,

        List<String> params,

        Long id
) {
}
