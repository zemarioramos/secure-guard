package com.z7design.secured_guard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.z7design.secured_guard.model.enums.ScheduleStatus;
import com.z7design.secured_guard.model.enums.Shift;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules")
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;
    
    @Column(nullable = false)
    private LocalDate scheduleDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Shift shift;
    
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;
    
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    
    @ManyToOne
    @JoinColumn(name = "patrol_id")
    private Patrol patrol;
    
    @Column(length = 500)
    private String observations;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
} 