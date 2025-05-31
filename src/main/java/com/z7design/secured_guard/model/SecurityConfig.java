package com.z7design.secured_guard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_config")
public class SecurityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer passwordMinLength = 8;

    @Column(nullable = false)
    private Boolean requireUppercase = true;

    @Column(nullable = false)
    private Boolean requireLowercase = true;

    @Column(nullable = false)
    private Boolean requireNumbers = true;

    @Column(nullable = false)
    private Boolean requireSpecialChars = true;

    @Column(nullable = false)
    private Integer sessionTimeoutMinutes = 30;

    @Column(nullable = false)
    private Integer maxLoginAttempts = 5;

    @Column(nullable = false)
    private Integer lockoutDurationMinutes = 15;

    @Column(nullable = false)
    private Boolean enableTwoFactor = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}