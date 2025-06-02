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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time_records")
public class TimeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference("employee-time-records")
    private Employee employee;
    
    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;
    
    @Column(name = "entry_time")
    private LocalTime entryTime;
    
    @Column(name = "exit_time")
    private LocalTime exitTime;
    
    @Column(name = "entry_lunch_time")
    private LocalTime entryLunchTime;
    
    @Column(name = "exit_lunch_time")
    private LocalTime exitLunchTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TimeRecordStatus status;
    
    @Column(name = "justification")
    private String justification;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Manual getters and setters
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public LocalDate getRecordDate() {
        return recordDate;
    }
    
    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }
    
    public LocalTime getEntryTime() {
        return entryTime;
    }
    
    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }
    
    public LocalTime getExitTime() {
        return exitTime;
    }
    
    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }
    
    public LocalTime getEntryLunchTime() {
        return entryLunchTime;
    }
    
    public void setEntryLunchTime(LocalTime entryLunchTime) {
        this.entryLunchTime = entryLunchTime;
    }
    
    public LocalTime getExitLunchTime() {
        return exitLunchTime;
    }
    
    public void setExitLunchTime(LocalTime exitLunchTime) {
        this.exitLunchTime = exitLunchTime;
    }
    
    public TimeRecordStatus getStatus() {
        return status;
    }
    
    public void setStatus(TimeRecordStatus status) {
        this.status = status;
    }
    
    public String getJustification() {
        return justification;
    }
    
    public void setJustification(String justification) {
        this.justification = justification;
    }
}