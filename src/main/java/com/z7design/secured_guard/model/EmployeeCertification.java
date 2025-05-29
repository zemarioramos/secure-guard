package com.z7design.secured_guard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.z7design.secured_guard.model.enums.CertificationStatus;

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
@Table(name = "employee_certifications")
public class EmployeeCertification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
    
    @Column(name = "certification_number")
    private String certificationNumber;
    
    @Column(name = "issue_date")
    private LocalDate issueDate;
    
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificationStatus status;
    
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