package com.z7design.secured_guard.service;

import com.z7design.secured_guard.dto.AuthenticationRequest;
import com.z7design.secured_guard.dto.AuthenticationResponse;
import com.z7design.secured_guard.dto.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);
} 