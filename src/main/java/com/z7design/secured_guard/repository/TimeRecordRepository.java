package com.z7design.secured_guard.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.TimeRecord;
import com.z7design.secured_guard.model.enums.TimeRecordStatus;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, UUID> {
    List<TimeRecord> findByEmployeeId(UUID employeeId);
    List<TimeRecord> findByEmployeeIdAndRecordDateBetween(UUID employeeId, LocalDate startDate, LocalDate endDate);
    List<TimeRecord> findByEmployeeIdAndStatus(UUID employeeId, TimeRecordStatus status);
    List<TimeRecord> findByRecordDateBetween(LocalDate startDate, LocalDate endDate);
} 