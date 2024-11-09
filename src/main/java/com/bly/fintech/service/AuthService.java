package com.bly.fintech.service;

import com.bly.fintech.model.User;
import com.bly.fintech.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean userExists(String username, String email) {
        return userRepository.findByUsername(username) != null || userRepository.findByEmail(email) != null;
    }

    public User registerUser(User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null  || user.getRole() == null) {
            throw new IllegalArgumentException("Username, password, email, or role cannot be null.");
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("User registration failed due to invalid arguments: " + ex.getMessage());
        }
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
