package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.ResponseDTO;
import com.example.myfirstapp.models.User;
import com.example.myfirstapp.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello world";
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseDTO<User>> getUsers() {
        log.info("Fetching users dhsjkdhaskj");
        List<User> users= userService.getAllUsers();
        ResponseDTO<User> response = new ResponseDTO<>("SUCCESS", HttpStatus.CREATED, users);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        try
        {
            userService.addUser(user);
            return new ResponseEntity<>("Success",HttpStatusCode.valueOf(200));
        } catch(Exception e){
            return new ResponseEntity<>("Failure:" + e.getMessage(),HttpStatusCode.valueOf(500));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable @Positive(message = "Id must be positive") Integer id, @Valid @RequestBody User user) {
        try
        {
            System.out.println(user);
            User updatedUser = userService.updateUser(user,id);
            return new ResponseEntity<>("Success",HttpStatusCode.valueOf(200));
        } catch(Exception e){
            return new ResponseEntity<>("Failure:" + e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable @Positive(message = "Id must be positive") Integer id) {
        try
        {
            userService.deleteUser(id);
            return new ResponseEntity<>("Success",HttpStatusCode.valueOf(200));
        } catch(Exception e){
            return new ResponseEntity<>("Failure:" + e.getMessage(),HttpStatusCode.valueOf(500));
        }
    }
}
