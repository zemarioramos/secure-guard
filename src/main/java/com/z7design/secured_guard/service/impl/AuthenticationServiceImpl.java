package com.z7design.secured_guard.service.impl;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(token)
            .user(mapToUserResponse(user))
            .build();
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getFullName());
        user.setRole(UserRole.VIGILANTE);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(token)
            .user(mapToUserResponse(user))
            .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .fullName(user.getName())
            .role(user.getRole().name())
            .build();
    }
} 