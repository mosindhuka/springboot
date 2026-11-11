package com.example.myfirstapp.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class UserAgent {

    private final ChatClient chatClient;
    private final UserTools userTools;

    public UserAgent(
            ChatClient.Builder chatClientBuilder,
            UserTools userTools) {

        this.chatClient = chatClientBuilder.build();
        this.userTools = userTools;
    }

    public String chat(String message) {

        return chatClient.prompt()
                .system(AgentInstructions.SYSTEM_PROMPT)
                .user(message)
                .tools(userTools)
                .call()
                .content();
    }
}