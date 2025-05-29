package com.z7design.secured_guard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.z7design.secured_guard.model.enums.TimeRecordStatus;

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
import lombok.Data;

@Data
@Entity
@Table(name = "time_records")
public class TimeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;
    
    @Column(nullable = false)
    private LocalDate recordDate;
    
    @Column(nullable = false)
    private LocalTime entryTime;
    
    @Column(nullable = false)
    private LocalTime exitTime;
    
    @Column
    private LocalTime entryLunchTime;
    
    @Column
    private LocalTime exitLunchTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeRecordStatus status;
    
    @Column(length = 500)
    private String justification;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
} 