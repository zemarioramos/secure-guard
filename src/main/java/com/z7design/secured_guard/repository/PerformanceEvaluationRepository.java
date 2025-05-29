package com.z7design.secured_guard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.PerformanceEvaluation;
import com.z7design.secured_guard.model.enums.EvaluationStatus;

@Repository
public interface PerformanceEvaluationRepository extends JpaRepository<PerformanceEvaluation, Long> {
    List<PerformanceEvaluation> findByEmployeeId(Long employeeId);
    List<PerformanceEvaluation> findByEvaluatorId(Long evaluatorId);
    List<PerformanceEvaluation> findByEmployeeIdAndStatus(Long employeeId, EvaluationStatus status);
    List<PerformanceEvaluation> findByEvaluationDateBetween(LocalDate startDate, LocalDate endDate);
    List<PerformanceEvaluation> findByEmployeeIdAndEvaluationDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
} 