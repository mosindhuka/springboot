package com.example.myfirstapp.controllers;

import com.example.myfirstapp.models.User;
import com.example.myfirstapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return service.login(user.getUsername(), user.getPassword());
    }
}