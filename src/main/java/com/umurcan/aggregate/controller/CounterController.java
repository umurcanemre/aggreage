package com.umurcan.aggregate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CounterController {
    private int counter = 0;
    private static final String instanceIdentifier = UUID.randomUUID().toString();

    @GetMapping
    public IdentifiedCounterResponse countInInstance(){
        return new IdentifiedCounterResponse(instanceIdentifier, counter++);
    }

    public record IdentifiedCounterResponse(String instanceIdentifier, int counter){};
}
