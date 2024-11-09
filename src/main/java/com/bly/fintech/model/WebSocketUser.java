package com.bly.fintech.model;

import java.security.Principal;

public class WebSocketUser implements Principal {
    private String username;
    private String role;

    public WebSocketUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
