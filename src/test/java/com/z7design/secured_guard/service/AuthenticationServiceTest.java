package com.z7design.secured_guard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.z7design.secured_guard.dto.AuthenticationResponse;
import com.z7design.secured_guard.dto.LoginRequest;
import com.z7design.secured_guard.dto.RegisterRequest;
import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.repository.UserRepository;
import com.z7design.secured_guard.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User testUser;
    private String testToken;
    private AuthenticationResponse testAuthResponse;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .fullName("Test User")
                .role("USER")
                .status("ATIVO")
                .build();

        testToken = "test.jwt.token";
        testAuthResponse = AuthenticationResponse.builder()
                .token(testToken)
                .user(com.z7design.secured_guard.dto.UserResponse.builder()
                        .username(testUser.getUsername())
                        .email(testUser.getEmail())
                        .fullName(testUser.getFullName())
                        .role(testUser.getRole())
                        .build())
                .build();
    }

    @Test
    void whenLoginWithValidCredentials_thenReturnToken() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        Authentication authentication = mock(Authentication.class);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(testUser));
        when(jwtTokenProvider.generateToken(any(User.class))).thenReturn(testToken);

        // Act
        AuthenticationResponse result = authenticationService.login(loginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(testToken, result.getToken());
        assertEquals(testUser.getUsername(), result.getUser().getUsername());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(testUser);
    }

    @Test
    void whenLoginWithInvalidCredentials_thenThrowException() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authenticationService.login(loginRequest);
        });
    }

    @Test
    void whenRegisterWithValidData_thenCreateUser() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest(
                "newuser",
                "new@example.com",
                "password",
                "New User"
        );

        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtTokenProvider.generateToken(any(User.class))).thenReturn(testToken);

        // Act
        AuthenticationResponse result = authenticationService.register(registerRequest);

        // Assert
        assertNotNull(result);
        assertEquals(testToken, result.getToken());
        assertEquals(testUser.getUsername(), result.getUser().getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void whenRegisterWithExistingUsername_thenThrowException() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest(
                "existinguser",
                "new@example.com",
                "password",
                "New User"
        );

        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authenticationService.register(registerRequest);
        });
    }

    @Test
    void whenRegisterWithExistingEmail_thenThrowException() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest(
                "newuser",
                "existing@example.com",
                "password",
                "New User"
        );

        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authenticationService.register(registerRequest);
        });
    }
} 