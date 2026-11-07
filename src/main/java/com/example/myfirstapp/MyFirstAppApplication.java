package com.example.myfirstapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MyFirstAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFirstAppApplication.class, args);
    }

}
