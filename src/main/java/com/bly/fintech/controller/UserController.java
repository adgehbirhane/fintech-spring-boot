package com.bly.fintech.controller;

import com.bly.fintech.model.User;
import com.bly.fintech.service.AuthService;
import com.bly.fintech.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        if (authService.userExists(user.getUsername(), user.getEmail())) {
            throw new IllegalArgumentException("Username or Email is already taken!");
        }
        User registeredUser = authService.registerUser(user);

        URI location = URI.create(String.format("/api/users/%s", registeredUser.getId()));
        return ResponseEntity.created(location).body(registeredUser);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Pageable pageable = PageRequest.of(page, size, sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        return userService.getAllUsers(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @Valid @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        boolean isDeleted = userService.deleteUserById(id);
        return isDeleted ? ResponseEntity.ok("User deleted successfully.") : ResponseEntity.notFound().build();
    }
}
