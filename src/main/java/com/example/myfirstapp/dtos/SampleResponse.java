package com.example.myfirstapp.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class SampleResponse {
    private Long id;
    private String name;
    private String email;
    Map<String, Object> json;
}
