package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.SampleRequest;
import com.example.myfirstapp.dtos.SampleResponse;
import com.example.myfirstapp.services.ExternalApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ExternalApiController {
    private ExternalApiService externalApiService;

    public void UserController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @PostMapping("/external-api")
    public SampleResponse createUser(@RequestBody SampleRequest request) {
        return externalApiService.createUser(request);
    }
}
