package com.bly.fintech.repository;

import com.bly.fintech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findManyByUsername(String username);
    Optional<User> findManyByEmail(String email);
    User findByUsername(String username);
    User findByEmail(String email);
}
