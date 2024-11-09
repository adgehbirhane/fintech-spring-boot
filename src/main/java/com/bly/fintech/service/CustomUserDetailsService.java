package com.bly.fintech.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // This is just a sample. You can fetch users from a database instead.
    @Override
    public UserDetails loadUserByUsername(String username) {
        // For simplicity, we use a hardcoded user. Replace with database fetching logic.
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}password")  // {noop} means no encoding; use bcrypt for production
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
