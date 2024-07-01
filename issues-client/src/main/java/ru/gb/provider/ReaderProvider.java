package ru.gb.provider;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderProvider {
    private final WebClient webClient;

    public ReaderProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

    public Optional<Reader> getReaderById(Long id) {
        Reader reader = webClient.get()
                .uri("http://readers-client/readers/{id}", id)
                .retrieve()
                .bodyToMono(Reader.class)
                .block();

        return Optional.ofNullable(reader);
    }
}