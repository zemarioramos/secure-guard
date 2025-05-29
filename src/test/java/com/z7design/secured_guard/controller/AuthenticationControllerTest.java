package com.z7design.secured_guard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.dto.AuthenticationResponse;
import com.z7design.secured_guard.dto.AuthenticationRequest;
import com.z7design.secured_guard.dto.RegisterRequest;
import com.z7design.secured_guard.dto.UserResponse;
import com.z7design.secured_guard.service.AuthenticationService;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;

    private UserResponse testUserResponse;
    private AuthenticationResponse testAuthResponse;
    private String testToken;

    @BeforeEach
    void setUp() {
        testUserResponse = UserResponse.builder()
                .username("testuser")
                .email("test@example.com")
                .fullName("Test User")
                .role("USER")
                .build();

        testToken = "test.jwt.token";

        testAuthResponse = AuthenticationResponse.builder()
                .token(testToken)
                .user(testUserResponse)
                .build();
    }

    @Test
    void whenLoginWithValidCredentials_thenReturnToken() throws Exception {
        // Arrange
        AuthenticationRequest loginRequest = new AuthenticationRequest("testuser", "password");
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(testAuthResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(testToken))
                .andExpect(jsonPath("$.user.username").value(testUserResponse.getUsername()))
                .andExpect(jsonPath("$.user.email").value(testUserResponse.getEmail()))
                .andExpect(jsonPath("$.user.fullName").value(testUserResponse.getFullName()))
                .andExpect(jsonPath("$.user.role").value(testUserResponse.getRole()));
    }

    @Test
    void whenRegisterWithValidData_thenReturnUser() throws Exception {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest(
                "newuser",
                "new@example.com",
                "password",
                "New User"
        );
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(testAuthResponse);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(testToken))
                .andExpect(jsonPath("$.user.username").value(testUserResponse.getUsername()))
                .andExpect(jsonPath("$.user.email").value(testUserResponse.getEmail()))
                .andExpect(jsonPath("$.user.fullName").value(testUserResponse.getFullName()))
                .andExpect(jsonPath("$.user.role").value(testUserResponse.getRole()));
    }

    @Test
    void whenLoginWithInvalidCredentials_thenReturnUnauthorized() throws Exception {
        // Arrange
        AuthenticationRequest loginRequest = new AuthenticationRequest("testuser", "wrongpassword");
        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenRegisterWithExistingUsername_thenReturnBadRequest() throws Exception {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest(
                "existinguser",
                "new@example.com",
                "password",
                "New User"
        );
        when(authenticationService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Username already exists"));

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }
} 