package com.z7design.secured_guard.service;

import java.util.List;
import java.util.UUID;

import com.z7design.secured_guard.model.User;

public interface UserService {
    List<User> findActiveUsers();
    User findById(UUID id);
    User findByEmail(String email);
    User save(User user);
    void delete(UUID id);
    List<User> findAll();
} 