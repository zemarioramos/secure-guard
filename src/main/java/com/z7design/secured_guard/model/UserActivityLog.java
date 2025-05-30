package com.z7design.secured_guard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_activity_logs")
public class UserActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "action")
    private String action;

    @Column(name = "details")
    private String details;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
} 