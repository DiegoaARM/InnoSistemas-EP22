package com.example.InnoSistemas.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    private final MeterRegistry registry;
    private final Counter myCounter;

    public MyController(MeterRegistry registry) {
        this.registry = registry;
        this.myCounter = registry.counter("my.custom.counter");
    }

    @GetMapping("/my-endpoint")
    public String myEndpoint() {
        myCounter.increment();
        return "Hello World";
    }
}
