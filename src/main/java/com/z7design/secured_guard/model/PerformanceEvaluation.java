package com.z7design.secured_guard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.z7design.secured_guard.model.enums.EvaluationStatus;
import com.z7design.secured_guard.model.enums.EvaluationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "performance_evaluations")
public class PerformanceEvaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "evaluator_id", nullable = false)
    private User evaluator;
    
    @Column(name = "evaluation_date", nullable = false)
    private LocalDate evaluationDate;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EvaluationType evaluationType;
    
    @Column(nullable = false)
    private Integer score;
    
    @Column(length = 1000)
    private String comments;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationStatus status;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
} 