package com.z7design.secured_guard.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "positions")
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
    
    @Column(name = "base_salary", nullable = false)
    private Double baseSalary;
    
    @OneToMany(mappedBy = "position")
    @JsonIgnoreProperties("position")
    private List<Employee> employees = new ArrayList<>();
    
    @OneToMany(mappedBy = "position")
    @JsonIgnoreProperties("position")
    private List<Benefit> benefits = new ArrayList<>();
    
    @OneToMany(mappedBy = "position")
    @JsonIgnoreProperties("position")
    private List<EPI> epis = new ArrayList<>();
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
} 