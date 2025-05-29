package com.z7design.secured_guard.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "units")
public class Unit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private String address;
    
    @Column
    private String phone;
    
    @Column
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Unit parent;
    
    @OneToMany(mappedBy = "parent")
    @JsonIgnoreProperties("parent")
    private List<Unit> children;
    
    @OneToMany(mappedBy = "unit")
    @JsonIgnoreProperties("unit")
    private List<Employee> employees;
    
    @OneToMany(mappedBy = "unit")
    @JsonIgnoreProperties("unit")
    private List<Position> positions;
    
    @OneToMany(mappedBy = "unit")
    @JsonIgnoreProperties({"position", "unit", "documents", "benefits", "scaleHistory", "occurrences", "payrolls", "epis"})
    private List<Payroll> payrolls;
    
    @OneToMany(mappedBy = "unit")
    private List<Location> locations;
    
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