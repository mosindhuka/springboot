package com.example.myfirstapp.services;

import com.example.myfirstapp.models.User;
import com.example.myfirstapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    final
    PasswordEncoder encoder;


    public UserService(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Cacheable("users")
    public List<User> getAllUsers() {
        System.out.println("DB CALL for id all users ");
        return userRepository.findAll();
    }

    public User addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User userRequest, Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(existingUser);
        existingUser.setName(userRequest.getName());
        existingUser.setGender(userRequest.getGender());
        existingUser.setDob(userRequest.getDob());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(encoder.encode(userRequest.getPassword()));

        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(existingUser);
    }
}
