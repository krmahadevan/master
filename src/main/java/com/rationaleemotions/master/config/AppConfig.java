package com.rationaleemotions.master.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.Optional;

@Configuration
@Slf4j
public class AppConfig {

    @Value("${application.worker.address}")
    private String workerAddress;

    @Bean
    public RestClient restClient() {
        RestClient.Builder builder = RestClient.builder().defaultHeader("dragon-warrior", "kungfu-panda");
        Optional.ofNullable(System.getProperty("http.proxyHost"))
                .ifPresent(host -> builder.requestFactory(factory(host)));
        return builder.baseUrl(workerAddress).build();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder().defaultHeader("dragon-warrior", "kungfu-panda");
        Optional.ofNullable(System.getProperty("http.proxyHost"))
                .ifPresent(host -> builder.requestFactory(() -> factory(host)));

        return builder.rootUri(workerAddress).build();
    }

    private SimpleClientHttpRequestFactory factory(String host) {
        log.info("Using proxy host: {}", host);
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        int port = Integer.parseInt(Objects.requireNonNull(System.getProperty("http.proxyPort", "8081")));
        SocketAddress address = new InetSocketAddress(host, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        factory.setProxy(proxy);
        return factory;
    }

}
