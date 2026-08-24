package com.example.myfirstapp.services;

import com.example.myfirstapp.dtos.SampleRequest;
import com.example.myfirstapp.dtos.SampleResponse;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class ExternalApiService {

    private final RestClient restClient;
    private final MeterRegistry registry;


    public ExternalApiService(RestClient restClient, MeterRegistry registry) {
        this.restClient = restClient;
        this.registry = registry;
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

    @Async("taskExecutor")
    public void processAsync(SampleRequest request) throws InterruptedException {
        try {
            // Long-running operation
            log.info("Processing completed");
            Thread.sleep(5000);
            System.out.println("Processing completed");

            registry.counter(
                    "async.processed",
                    "status", "success"
            ).increment();

        } catch (Exception e) {
            registry.counter(
                    "async.processed",
                    "status", "failure"
            ).increment();

            throw e;
        }

    }
}
