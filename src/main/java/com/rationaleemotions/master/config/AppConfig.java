package com.rationaleemotions.master.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

@Configuration
public class AppConfig {

    @Value("${application.worker.address}")
    private String workerAddress;

    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        SocketAddress address = new InetSocketAddress(
                System.getProperty("http.proxyHost"),
                Integer.parseInt(System.getProperty("http.proxyPort"))
        );
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        factory.setProxy(proxy);
        return RestClient.builder()
                .requestFactory(factory)
                .baseUrl(workerAddress).build();
    }

}
