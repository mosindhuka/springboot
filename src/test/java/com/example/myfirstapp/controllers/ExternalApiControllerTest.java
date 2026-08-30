package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.SampleRequest;
import com.example.myfirstapp.dtos.SampleResponse;
import com.example.myfirstapp.services.ExternalApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalApiControllerTest {

    @Mock
    private ExternalApiService externalApiService;

    @InjectMocks
    private ExternalApiController externalApiController;


    @Test
    void callExternalApi_shouldReturnSampleResponse() {

        // Arrange
        SampleRequest request = new SampleRequest();
        SampleResponse expectedResponse = new SampleResponse();

        when(externalApiService.callApi(request))
                .thenReturn(expectedResponse);

        // Act
        SampleResponse actualResponse =
                externalApiController.callExternalApi(request);

        // Assert
        assertEquals(expectedResponse, actualResponse);

        verify(externalApiService, times(1))
                .callApi(request);
    }


    @Test
    void asyncApi_shouldReturnRequestAccepted() throws InterruptedException {

        // Arrange
        SampleRequest request = new SampleRequest();

        doNothing()
                .when(externalApiService)
                .processAsync(request);

        // Act
        ResponseEntity<String> response =
                externalApiController.asyncApi(request);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Request accepted", response.getBody());

        verify(externalApiService, times(1))
                .processAsync(request);
    }
}