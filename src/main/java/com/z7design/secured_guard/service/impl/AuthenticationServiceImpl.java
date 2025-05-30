package com.z7design.secured_guard.service.impl;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.z7design.secured_guard.dto.AuthenticationRequest;
import com.z7design.secured_guard.dto.AuthenticationResponse;
import com.z7design.secured_guard.dto.RegisterRequest;
import com.z7design.secured_guard.dto.UserResponse;
import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.model.enums.UserRole;
import com.z7design.secured_guard.model.enums.UserStatus;
import com.z7design.secured_guard.repository.UserRepository;
import com.z7design.secured_guard.security.JwtService;
import com.z7design.secured_guard.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new RuntimeException("Full name is required");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .name(request.getFullName().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : UserRole.VIGILANTE)
                .status(UserStatus.ACTIVE)
                .active(true)
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        
        return AuthenticationResponse.builder()
                .token(token)
                .user(UserResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .fullName(user.getName())
                        .role(user.getRole())
                        .build())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtService.generateToken(user);
        
        return AuthenticationResponse.builder()
                .token(token)
                .user(UserResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .fullName(user.getName())
                        .role(user.getRole())
                        .build())
                .build();
    }

    @Override
    public User getCurrentUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
} 