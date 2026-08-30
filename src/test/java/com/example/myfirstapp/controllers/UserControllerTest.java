package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.ResponseDTO;
import com.example.myfirstapp.dtos.UserDetails;
import com.example.myfirstapp.enums.Gender;
import com.example.myfirstapp.models.User;
import com.example.myfirstapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @Test
    void greeting_shouldReturnHelloWorld() {

        // Act
        String result = userController.greeting();

        // Assert
        assertEquals("Hello world", result);
    }


    @Test
    void getUsers_shouldReturnUsers() {

        // Arrange
        List<UserDetails> users = List.of(
                new UserDetails(1, "John", "Mumbai"),
                new UserDetails(2, "Jane", "Pune")
        );

        when(userService.getAllUsers())
                .thenReturn(users);

        // Act
        ResponseEntity<ResponseDTO<UserDetails>> response =
                userController.getUsers();

        // Assert
        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                "SUCCESS",
                response.getBody().getStatus()
        );

        assertEquals(
                HttpStatus.CREATED,
                response.getBody().getStatus_code()
        );

        assertEquals(
                users,
                response.getBody().getData()
        );

        verify(userService, times(1))
                .getAllUsers();
    }


    @Test
    void createUser_shouldReturnSuccess() {

        // Arrange
        User user = createUser();

        when(userService.addUser(any(User.class)))
                .thenReturn(user);

        // Act
        ResponseEntity<Object> response =
                userController.createUser(user);

        // Assert
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertEquals(
                "Success",
                response.getBody()
        );

        verify(userService, times(1))
                .addUser(user);
    }


    @Test
    void createUser_shouldReturnFailureWhenServiceThrowsException() {

        // Arrange
        User user = createUser();

        when(userService.addUser(any(User.class)))
                .thenThrow(
                        new RuntimeException("Database error")
                );

        // Act
        ResponseEntity<Object> response =
                userController.createUser(user);

        // Assert
        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertEquals(
                "Failure:Database error",
                response.getBody()
        );

        verify(userService, times(1))
                .addUser(user);
    }


    @Test
    void updateUser_shouldReturnSuccess() {

        // Arrange
        User user = createUser();

        when(userService.updateUser(user, 1))
                .thenReturn(user);

        // Act
        ResponseEntity<Object> response =
                userController.updateUser(1, user);

        // Assert
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertEquals(
                "Success",
                response.getBody()
        );

        verify(userService, times(1))
                .updateUser(user, 1);
    }


    @Test
    void updateUser_shouldReturnFailureWhenServiceThrowsException() {

        // Arrange
        User user = createUser();

        when(userService.updateUser(user, 1))
                .thenThrow(
                        new RuntimeException("User not found")
                );

        // Act
        ResponseEntity<Object> response =
                userController.updateUser(1, user);

        // Assert
        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertEquals(
                "Failure:User not found",
                response.getBody()
        );

        verify(userService, times(1))
                .updateUser(user, 1);
    }


    @Test
    void deleteUser_shouldReturnSuccess() {

        // Arrange
        doNothing()
                .when(userService)
                .deleteUser(1);

        // Act
        ResponseEntity<Object> response =
                userController.deleteUser(1);

        // Assert
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertEquals(
                "Success",
                response.getBody()
        );

        verify(userService, times(1))
                .deleteUser(1);
    }


    @Test
    void deleteUser_shouldReturnFailureWhenServiceThrowsException() {

        // Arrange
        doThrow(
                new RuntimeException("User not found")
        )
                .when(userService)
                .deleteUser(1);

        // Act
        ResponseEntity<Object> response =
                userController.deleteUser(1);

        // Assert
        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertEquals(
                "Failure:User not found",
                response.getBody()
        );

        verify(userService, times(1))
                .deleteUser(1);
    }


    private User createUser() {

        User user = new User();

        user.setName("John");
        user.setGender(Gender.MALE);
        user.setDob(LocalDate.of(1995, 1, 1));
        user.setEmail("john@example.com");
        user.setPassword("password123");

        return user;
    }
}