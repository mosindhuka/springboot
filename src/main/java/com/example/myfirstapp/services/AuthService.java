package com.example.myfirstapp.services;

import com.example.myfirstapp.models.User;
import com.example.myfirstapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final
    UserRepository repo;

    final
    PasswordEncoder encoder;

    final
    JwtService jwt;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtService jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public String login(String email, String password) {
        User user = repo.findByEmail(email)
                .orElseThrow();

        if (encoder.matches(password, user.getPassword())) {
            return jwt.generateToken(email);
        }

        throw new RuntimeException("Invalid login");
    }
}