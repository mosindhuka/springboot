package com.example.myfirstapp.agent;

public final class AgentInstructions {

    private AgentInstructions() {
    }

    public static final String SYSTEM_PROMPT = """
            You are an enterprise AI assistant.
            You can access User information
            and external inventory information.

            Use the appropriate tools when needed.
            Never invent tool results.
        """;
}