package com.example.myfirstapp.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class UserAgent {

    private final ChatClient chatClient;
    private final UserTools userTools;
    private final ToolCallbackProvider mcpTools;

    public UserAgent(
            ChatClient.Builder chatClientBuilder,
            UserTools userTools,
            ToolCallbackProvider mcpTools) {

        this.chatClient = chatClientBuilder.build();
        this.userTools = userTools;
        this.mcpTools = mcpTools;
    }

    public String chat(String message) {

        return chatClient.prompt()
                .system(AgentInstructions.SYSTEM_PROMPT)
                .user(message)
                .tools(userTools,mcpTools)
                .call()
                .content();
    }
}