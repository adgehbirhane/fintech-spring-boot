package com.bly.fintech.payload;

public class JwtResponse {

    private String token;
    private String type = "Bearer";

    // Parameterized constructor
    public JwtResponse(String token) {
        this.token = token;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
