package com.rationaleemotions.master.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
public class IngressController {

    private final RestClient client;

    @GetMapping(path = "/greet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> greeting() {
        Greeting downstream = Optional.ofNullable(client.get().uri("/worker/greet").retrieve().body(Greeting.class))
                .orElse(new Greeting("UNKNOWN"));
        return ResponseEntity.ok(new Greeting("Hello " + downstream.message()));
    }

    public record Greeting(String message) {
    }
}
