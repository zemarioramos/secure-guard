package com.z7design.secured_guard.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Unit parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("parent")
    @Builder.Default
    private List<Unit> children = new ArrayList<>();
    
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unit")
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();
    
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("unit")
    @Builder.Default
    private List<Position> positions = new ArrayList<>();
    
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"position", "unit", "documents", "benefits", "scaleHistory", "occurrences", "payrolls", "epis"})
    @Builder.Default
    private List<Payroll> payrolls = new ArrayList<>();
    
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Location> locations = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
} 