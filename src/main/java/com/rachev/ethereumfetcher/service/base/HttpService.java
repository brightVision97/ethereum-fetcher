package com.rachev.ethereumfetcher.service.base;

import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public interface HttpService {

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

    default WebClient getWebClientForAnotherNetwork(String network, String baseGoerliUrl) {
        return WebClient.builder()
                .baseUrl(baseGoerliUrl.replace("goerli", network))
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }
}
