package com.z7design.secured_guard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logs_atividade_usuario")
public class UserActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario")
    private String username;

    @Column(name = "acao")
    private String action;

    @Column(name = "detalhes")
    private String details;

    @Column(name = "data_hora")
    private LocalDateTime timestamp;
} 