package com.example.myfirstapp.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsTest {

    @Test
    void constructor_shouldSetAllFields() {

        // Arrange
        Integer userId = 1;
        String name = "John";
        String city = "Mumbai";

        // Act
        UserDetails userDetails =
                new UserDetails(userId, name, city);

        // Assert
        assertEquals(userId, userDetails.getUserId());
        assertEquals(name, userDetails.getName());
        assertEquals(city, userDetails.getCity());
    }

    @Test
    void setters_shouldUpdateFields() {

        // Arrange
        UserDetails userDetails =
                new UserDetails(1, "John", "Mumbai");

        // Act
        userDetails.setUserId(2);
        userDetails.setName("Jane");
        userDetails.setCity("Pune");

        // Assert
        assertEquals(2, userDetails.getUserId());
        assertEquals("Jane", userDetails.getName());
        assertEquals("Pune", userDetails.getCity());
    }
}