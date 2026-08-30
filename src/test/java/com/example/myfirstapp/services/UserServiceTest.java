package com.example.myfirstapp.services;

import com.example.myfirstapp.dtos.UserDetails;
import com.example.myfirstapp.enums.Gender;
import com.example.myfirstapp.models.User;
import com.example.myfirstapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;


    // ---------------------------------------------------------
    // getAllUsers()
    // ---------------------------------------------------------

    @Test
    void getAllUsers_shouldReturnUsers() {

        // Arrange
        List<UserDetails> expectedUsers = List.of(
                new UserDetails(1, "John", "Mumbai"),
                new UserDetails(2, "Cena", "Delhi")
        );

        when(userRepository.findAllUsers())
                .thenReturn(expectedUsers);

        // Act
        List<UserDetails> actualUsers = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);

        verify(userRepository, times(1))
                .findAllUsers();
    }


    // ---------------------------------------------------------
    // addUser()
    // ---------------------------------------------------------

    @Test
    void addUser_shouldEncodePasswordAndSaveUser() {

        // Arrange
        User user = new User();
        user.setPassword("password123");

        String encodedPassword = "encodedPassword";

        when(encoder.encode("password123"))
                .thenReturn(encodedPassword);

        when(userRepository.save(user))
                .thenReturn(user);

        // Act
        User result = userService.addUser(user);

        // Assert
        assertEquals(user, result);
        assertEquals(encodedPassword, user.getPassword());

        verify(encoder, times(1))
                .encode("password123");

        verify(userRepository, times(1))
                .save(user);
    }


    // ---------------------------------------------------------
    // updateUser()
    // ---------------------------------------------------------

    @Test
    void updateUser_shouldUpdateExistingUser() {

        // Arrange
        Integer id = 1;

        User existingUser = new User();
        existingUser.setName("Old Name");
        existingUser.setGender(Gender.MALE);
        existingUser.setEmail("old@example.com");
        existingUser.setPassword("oldPassword");

        User userRequest = new User();
        userRequest.setName("New Name");
        userRequest.setGender(Gender.FEMALE);
        userRequest.setEmail("new@example.com");
        userRequest.setPassword("newPassword");

        when(userRepository.findById(id))
                .thenReturn(Optional.of(existingUser));

        when(encoder.encode("newPassword"))
                .thenReturn("encodedNewPassword");

        when(userRepository.save(existingUser))
                .thenReturn(existingUser);

        // Act
        User result = userService.updateUser(userRequest, id);

        // Assert
        assertEquals(existingUser, result);

        assertEquals("New Name", existingUser.getName());
        assertEquals(Gender.FEMALE, existingUser.getGender());
        assertEquals("new@example.com", existingUser.getEmail());
        assertEquals("encodedNewPassword", existingUser.getPassword());

        verify(userRepository, times(1))
                .findById(id);

        verify(encoder, times(1))
                .encode("newPassword");

        verify(userRepository, times(1))
                .save(existingUser);
    }



    @Test
    void updateUser_shouldThrowExceptionWhenUserDoesNotExist() {

        // Arrange
        Integer id = 1;

        User userRequest = new User();

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.updateUser(userRequest, id)
        );

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1))
                .findById(id);

        verify(userRepository, never())
                .save(any(User.class));

        verify(encoder, never())
                .encode(anyString());
    }


    // ---------------------------------------------------------
    // deleteUser()
    // ---------------------------------------------------------

    @Test
    void deleteUser_shouldDeleteExistingUser() {

        // Arrange
        Integer id = 1;

        User existingUser = new User();

        when(userRepository.findById(id))
                .thenReturn(Optional.of(existingUser));

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userRepository, times(1))
                .findById(id);

        verify(userRepository, times(1))
                .delete(existingUser);
    }


    @Test
    void deleteUser_shouldThrowExceptionWhenUserDoesNotExist() {

        // Arrange
        Integer id = 1;

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.deleteUser(id)
        );

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1))
                .findById(id);

        verify(userRepository, never())
                .delete(any(User.class));
    }
}