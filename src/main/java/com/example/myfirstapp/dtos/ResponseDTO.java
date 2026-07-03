package com.example.myfirstapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO<T> {
    private String status;
    private HttpStatus status_code;
    private List<T> data;
}
