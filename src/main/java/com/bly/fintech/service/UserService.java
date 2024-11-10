package com.bly.fintech.service;

import com.bly.fintech.model.User;
import com.bly.fintech.repository.UserRepository;
import com.bly.fintech.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bly.fintech.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(UUID id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).orElse(null);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
            if (updatedUser.getEmail() != null) {
                if (!Utils.isValidEmail(updatedUser.getEmail())) {
                    throw new IllegalArgumentException("Invalid email format.");
                }
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null) user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!"));
    }

    public boolean deleteUserById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
