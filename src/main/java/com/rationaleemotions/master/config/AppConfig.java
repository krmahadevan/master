package com.rationaleemotions.master.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

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
        RestClient.Builder builder = RestClient.builder();
        Optional.ofNullable(System.getProperty("http.proxyHost"))
                .ifPresent(
                        host -> {
                            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
                            int port = Integer.parseInt(Objects.requireNonNull(System.getProperty("http.proxyPort")));
                            SocketAddress address = new InetSocketAddress(host, port);
                            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
                            factory.setProxy(proxy);
                            builder.requestFactory(factory);
                        }
                );
        return builder.baseUrl(workerAddress).build();
    }

}
