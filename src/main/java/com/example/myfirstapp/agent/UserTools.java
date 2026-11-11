package com.example.myfirstapp.agent;

import com.example.myfirstapp.dtos.UserDetails;
import com.example.myfirstapp.models.test.User;
import com.example.myfirstapp.services.UserService;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class UserTools {

    private final UserService userService;

    public UserTools(UserService userService) {
        this.userService = userService;
    }

    @Tool(
            name = "getUserInfo",
            description = """
            Retrieve the User details.
            This tool automatically identifies the authenticated
            employee. Do not request or provide an employee ID.
            """
    )
    public UserDetails getUserInfo() {

        return userService.getUser(1);
    }
}