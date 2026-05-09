package com.example.myfirstapp.services;

import com.example.myfirstapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository repo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired JwtService jwt;

    public String login(String username, String password) {
        User user = repo.findByUsername(username)
                .orElseThrow();

        if (encoder.matches(password, user.getPassword())) {
            return jwt.generateToken(username);
        }

        throw new RuntimeException("Invalid login");
    }
}