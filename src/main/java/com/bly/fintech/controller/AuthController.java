package com.bly.fintech.controller;

import com.bly.fintech.model.User;
import com.bly.fintech.dto.request.LoginRequestDto;
import com.bly.fintech.dto.response.JwtResponseDto;
import com.bly.fintech.service.AuthService;
import com.bly.fintech.util.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@Valid @RequestBody User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Username, password, or email cannot be null.");
        }

        if (authService.userExists(user.getUsername(), user.getEmail())) {
            throw new IllegalArgumentException("Username or Email is already taken!");
        }

        user.setRole("USER"); // Ensure only "USER" role is registered through signup
        User registeredUser = authService.registerUser(user);

        // Build the location URI for the newly created user
        URI location = URI.create(String.format("/api/users/%s", registeredUser.getId()));
        return ResponseEntity.created(location).body(registeredUser);
    }

    // Login Endpoint
    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Username or password cannot be null.");
        }

        User user = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        String token = jwtUtils.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
