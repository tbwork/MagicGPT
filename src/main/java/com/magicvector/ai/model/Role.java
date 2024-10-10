package com.magicvector.ai.model;

public enum Role {
    SYSTEM("system", "System Role"),
    USER("user", "User Role"),
    ASSISTANT("assistant", "Assistant Role");

    private final String value;
    private final String description;

    Role(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
