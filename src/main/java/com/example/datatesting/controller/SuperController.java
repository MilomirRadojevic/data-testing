package com.example.datatesting.controller;


import com.example.datatesting.service.ProjectionFetchingService;
import com.example.datatesting.service.SuperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SuperController {
    private final SuperService superService;
    private final ProjectionFetchingService projectionFetchingService;

    @PostConstruct
    void init() {
//        projectionFetchingService.addOrganizationsAndPeople();
    }

    @GetMapping("/directFetching")
    public void fetching() {
        superService.directFetching();
    }

//    @GetMapping("/fetchingReadWrite")
//    public void fetchingReadWrite() {
//        superService.fetchingReadWrite();
//    }

//    @GetMapping("/fetchingReadOnly")
//    public void fetchingReadOnly() {
//        superService.fetchingReadOnly();
//    }

    @GetMapping("/projectionFetching/1")
    public void findAllByMotto() {
        projectionFetchingService.findAllByMotto();
    }

    @GetMapping("/projectionFetching/2")
    public void findDynamicProjectionsByMotto() throws JsonProcessingException {
        projectionFetchingService.findDynamicProjectionsByMotto();
    }

    @GetMapping("/projectionFetching/3")
    public void projectionIncludingManyToOneRelationship() throws JsonProcessingException {
        projectionFetchingService.projectionIncludingManyToOneRelationship();
    }

    @GetMapping("/projectionFetching/4")
    public void projectionIncludingOneToManyRelationship() throws JsonProcessingException {
        projectionFetchingService.projectionIncludingOneToManyRelationship();
    }
}
