package com.z7design.secured_guard.service;

import java.util.UUID;

import com.z7design.secured_guard.dto.AuthenticationRequest;
import com.z7design.secured_guard.dto.AuthenticationResponse;
import com.z7design.secured_guard.dto.RegisterRequest;
import com.z7design.secured_guard.model.User;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    User getCurrentUser(UUID userId);
} 