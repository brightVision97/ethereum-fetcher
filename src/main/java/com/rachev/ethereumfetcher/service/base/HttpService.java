package com.rachev.ethereumfetcher.service.base;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public interface HttpService {

    int WEB_CLIENT_BUFFER_SIZE = 16 * 1024 * 1024;

    default <B, R> R sendPostRequestWithBody(WebClient webClient, Class<R> returnType, B body, Class<B> bodyType) {
        return webClient.post()
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
                .body(Mono.just(body), bodyType)
                .retrieve()
                .bodyToMono(returnType)
                .onErrorMap(e -> {
                    throw new RuntimeException(e.getMessage());
                })
                .block();
    }

    default WebClient getWebClientForNetworkOnDemand(@NotNull String network, String baseGoerliUrl) {
        return WebClient.builder()
                .baseUrl(baseGoerliUrl.replace("goerli", network))
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(WEB_CLIENT_BUFFER_SIZE))
                        .build())
                .build();
    }
}
