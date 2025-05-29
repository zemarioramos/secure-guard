package com.z7design.secured_guard.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.ScaleHistory;

@Repository
public interface ScaleHistoryRepository extends JpaRepository<ScaleHistory, UUID> {
    
    List<ScaleHistory> findByEmployeeId(UUID employeeId);
    
    List<ScaleHistory> findByScale(String scale);
    
    List<ScaleHistory> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<ScaleHistory> findByEmployeeIdAndStartDateBetween(UUID employeeId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<ScaleHistory> findByEndDateIsNull();
} 