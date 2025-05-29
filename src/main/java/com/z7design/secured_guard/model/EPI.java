package com.z7design.secured_guard.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "epis")
public class EPI {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @Column(name = "issue_date")
    private LocalDateTime issueDate;
    
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EPIStatus status;
    
    @Column(name = "document_url")
    private String documentUrl;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 