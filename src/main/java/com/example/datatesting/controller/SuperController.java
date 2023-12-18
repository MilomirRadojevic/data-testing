package com.example.datatesting.controller;

import com.example.datatesting.service.SuperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SuperController {
    private final SuperService superService;

    @GetMapping("/relationships")
    public void relationships() {
        superService.relationships();
    }

    @GetMapping("/directFetching")
    public void fetching() {
        superService.directFetching();
    }

    @GetMapping("/fetchingReadOnlyFlag")
    public void fetchingReadOnlyFlag() {
        superService.fetchingReadOnlyFlag();
    }

    @GetMapping("/projectionFetching")
    public void projectionFetching() throws JsonProcessingException {
        superService.projectionFetching();
    }
}
