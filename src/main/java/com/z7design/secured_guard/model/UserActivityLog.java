package com.z7design.secured_guard.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_activity_logs")
public class UserActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String action;

    @Column(length = 500)
    private String details;

    @CreationTimestamp
    private LocalDateTime timestamp;
} 