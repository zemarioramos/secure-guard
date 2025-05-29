package com.z7design.secured_guard.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Leave;
import com.z7design.secured_guard.model.enums.LeaveStatus;
import com.z7design.secured_guard.model.enums.LeaveType;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, UUID> {
    List<Leave> findByEmployeeId(UUID employeeId);
    List<Leave> findByEmployeeIdAndStatus(UUID employeeId, LeaveStatus status);
    List<Leave> findByEmployeeIdAndType(UUID employeeId, LeaveType type);
    List<Leave> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
} 