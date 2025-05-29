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
@Table(name = "registros_ponto")
public class TimeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Employee employee;
    
    @Column(name = "data_registro", nullable = false)
    private LocalDate recordDate;
    
    @Column(name = "hora_entrada")
    private LocalTime entryTime;
    
    @Column(name = "hora_saida")
    private LocalTime exitTime;
    
    @Column(name = "hora_entrada_almoco")
    private LocalTime entryLunchTime;
    
    @Column(name = "hora_saida_almoco")
    private LocalTime exitLunchTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TimeRecordStatus status;
    
    @Column(name = "justificativa")
    private String justification;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
} 