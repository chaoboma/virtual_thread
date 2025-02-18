package com.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * @author
 * @create 2024/10/19 下午 08:05
 * @Description
 **/
@RestController
@RequestMapping("/api")
public class WebFluxController {
    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Hello, Mono!");
    }
    @GetMapping("/flux")
    public Flux<String> getFlux() {
        return Flux.just("Hello", "World", "From", "WebFlux", "Controller", "in", "Spring Boot 3!");
    }
}
