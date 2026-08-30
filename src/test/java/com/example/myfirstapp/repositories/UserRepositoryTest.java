package com.example.myfirstapp.repositories;

import com.example.myfirstapp.dtos.UserDetails;
import com.example.myfirstapp.enums.Gender;
import com.example.myfirstapp.models.User;
import com.example.myfirstapp.models.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;


    // ---------------------------------------------------------
    // findByEmail()
    // ---------------------------------------------------------

    @Test
    void findByEmail_shouldReturnUserWhenEmailExists() {

        // Arrange
        User user = createUser(
                "John",
                "john@example.com"
        );

        userRepository.save(user);

        // Act
        Optional<User> result =
                userRepository.findByEmail("john@example.com");

        // Assert
        assertTrue(result.isPresent());

        assertEquals(
                "john@example.com",
                result.get().getEmail()
        );

        assertEquals(
                "John",
                result.get().getName()
        );
    }


    @Test
    void findByEmail_shouldReturnEmptyWhenEmailDoesNotExist() {

        // Act
        Optional<User> result =
                userRepository.findByEmail(
                        "doesnotexist@example.com"
                );

        // Assert
        assertTrue(result.isEmpty());
    }


    // ---------------------------------------------------------
    // findAllUsers()
    // ---------------------------------------------------------

    @Test
    void findAllUsers_shouldReturnUserDetails() {

        // Arrange

        User user = createUser(
                "John",
                "john@example.com"
        );

        userRepository.save(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setCity("Mumbai");
        userInfo.setState("Maharashtra");
        userInfo.setPincode("400001");

        userInfoRepository.save(userInfo);


        // Act
        List<UserDetails> result =
                userRepository.findAllUsers();


        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        UserDetails userDetails = result.get(0);

        assertEquals(
                user.getId(),
                userDetails.getUserId()
        );

        assertEquals(
                "John",
                userDetails.getName()
        );

        assertEquals(
                "Mumbai",
                userDetails.getCity()
        );
    }


    @Test
    void findAllUsers_shouldReturnEmptyWhenNoUsersExist() {

        // Act
        List<UserDetails> result =
                userRepository.findAllUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    // ---------------------------------------------------------
    // Helper method
    // ---------------------------------------------------------

    private User createUser(String name, String email) {

        User user = new User();

        user.setName(name);
        user.setGender(Gender.MALE);
        user.setDob(
                LocalDate.of(1995, 1, 1)
        );
        user.setEmail(email);
        user.setPassword("password123");

        return user;
    }
}