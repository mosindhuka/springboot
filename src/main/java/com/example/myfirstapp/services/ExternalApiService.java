package com.example.myfirstapp.services;

import com.example.myfirstapp.dtos.SampleRequest;
import com.example.myfirstapp.dtos.SampleResponse;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class ExternalApiService {

    private final RestClient restClient;

    public ExternalApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retry(name = "externalApi")
    public SampleResponse callApi(SampleRequest request) {
        log.info("external api call");
        SampleResponse sr = restClient.post()
                .uri("https://httpbin.org/post")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(SampleResponse.class);
        log.info(String.valueOf(sr));
        return sr;
    }
}
