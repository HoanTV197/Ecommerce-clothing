package com.example.ecommerceclothing.dto;

public class AuthResponse {
    private String message;
    private String token;
    private String username;

    public AuthResponse(String message, String token, String username) {
        this.message = message;
        this.token = token;
        this.username = username;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
