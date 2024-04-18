package com.rationaleemotions.master.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
@Slf4j
public class IngressController {

    private final RestClient client;

    @GetMapping(path = "/greet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GreetingOutBound> greeting() {
        List<String> dragonWarrior = new ArrayList<>();
        GreetingInBound reply = client.get()
                .uri("/worker/greet")
                .exchange((clientRequest, clientResponse) -> {
                    HttpHeaders headers = clientResponse.getHeaders();
                    dragonWarrior.addAll(headers.getOrEmpty("dragon-warrior"));
                    log.info("Found dragon-warrior: {}", dragonWarrior);
                    return Objects.requireNonNull(clientResponse.bodyTo(GreetingInBound.class));
                });

        return ResponseEntity.ok(new GreetingOutBound("Hello " + reply.message(),
                String.join(", ", dragonWarrior)));
    }

    public record GreetingInBound(String message) {
    }

    public record GreetingOutBound(String message, String dragonWarrior) {
    }
}
