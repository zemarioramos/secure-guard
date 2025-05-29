package com.z7design.secured_guard.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "payrolls")
public class Payroll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference("employee-payrolls")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonIgnoreProperties({"employees", "payrolls"})
    private Unit unit;
    
    @Column(name = "reference_month")
    private String referenceMonth;
    
    @Column(name = "base_salary")
    private Double baseSalary;
    
    @Column(name = "gross_salary")
    private Double grossSalary;
    
    @Column(name = "net_salary")
    private Double netSalary;
    
    @Column(name = "overtime_hours")
    private Double overtimeHours;
    
    @Column(name = "overtime_value")
    private Double overtimeValue;
    
    @Column(name = "benefits_value")
    private Double benefitsValue;
    
    @Column(name = "deductions_value")
    private Double deductionsValue;
    
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