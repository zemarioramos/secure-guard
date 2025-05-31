package com.z7design.secured_guard.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

public interface UserService {
    List<User> findActiveUsers();
    User findById(UUID id);
    User findByEmail(String email);
    User save(User user);
    void delete(UUID id);
    List<User> findAll();
}

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        if (!StringUtils.hasText(user.getName())) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(UUID id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!StringUtils.hasText(user.getName())) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setRole(user.getRole());
        existingUser.setStatus(user.getStatus());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findActiveUsers() {
        // Implementation needed
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public User findById(UUID id) {
        // Implementation needed
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public User findByEmail(String email) {
        // Implementation needed
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public User save(User user) {
        // Implementation needed
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> findAll() {
        // Implementation needed
        throw new UnsupportedOperationException("Method not implemented");
    }
} 