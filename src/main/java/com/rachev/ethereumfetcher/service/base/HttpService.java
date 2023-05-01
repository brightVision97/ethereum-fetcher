package com.rachev.ethereumfetcher.service.base;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
}
