package com.z7design.secured_guard.service.impl;

import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.repository.UserRepository;
import com.z7design.secured_guard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public List<User> findActiveUsers() {
        return userRepository.findByActiveTrue();
    }
    
    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Override
    @Transactional
    public User save(User user) {
        if (!StringUtils.hasText(user.getName())) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
} 