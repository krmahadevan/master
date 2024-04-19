package com.rationaleemotions.master.api;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
@Slf4j
public class IngressController {

    public static final String PATH = "/worker/greet";
    private final RestTemplate restTemplate;
    private final RestClient restClient;

    @GetMapping(path = "/greet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GreetingOutBound> greeting(@RequestHeader HttpHeaders headers) {
        boolean useRestClient = !headers.getOrEmpty("rest-client").isEmpty();
        ResponseEntity<Greeting> repliedEntity = useRestClient ? greetUsingRestClient() : greetUsingRestTemplate();

        Greeting reply = Objects.requireNonNull(repliedEntity.getBody());

        log.info("Greeting: {}", reply);
        HttpHeaders repliedHeaders = repliedEntity.getHeaders();

        GreetingOutBound outBound =
                new GreetingOutBound("Hello " + reply.message(), repliedHeaders.toString());

        return ResponseEntity.ok().headers(repliedHeaders).body(outBound);
    }

    private ResponseEntity<Greeting> greetUsingRestClient() {
        log.info("Greeting using RestClient");
        return restClient.get().uri(PATH).retrieve().toEntity(Greeting.class);
    }

    private ResponseEntity<Greeting> greetUsingRestTemplate() {
        log.info("Greeting using RestTemplate");
        return restTemplate.getForEntity(PATH, Greeting.class);
    }

    public record GreetingOutBound(String message, String dragonWarrior) {
    }

    public record Greeting(String message) {
    }
}
