package com.z7design.secured_guard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.EmployeeCertification;
import com.z7design.secured_guard.model.enums.CertificationStatus;

@Repository
public interface EmployeeCertificationRepository extends JpaRepository<EmployeeCertification, Long> {
    List<EmployeeCertification> findByEmployeeId(Long employeeId);
    List<EmployeeCertification> findByEmployeeIdAndStatus(Long employeeId, CertificationStatus status);
    List<EmployeeCertification> findByTrainingId(Long trainingId);
    List<EmployeeCertification> findByExpirationDateBefore(LocalDate date);
    List<EmployeeCertification> findByExpirationDateBetween(LocalDate startDate, LocalDate endDate);
} 