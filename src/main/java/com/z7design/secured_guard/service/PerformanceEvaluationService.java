package com.z7design.secured_guard.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.PerformanceEvaluation;
import com.z7design.secured_guard.model.enums.EvaluationStatus;
import com.z7design.secured_guard.repository.PerformanceEvaluationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerformanceEvaluationService {
    
    private final PerformanceEvaluationRepository evaluationRepository;
    
    @Transactional
    public PerformanceEvaluation create(PerformanceEvaluation evaluation) {
        validateEvaluation(evaluation);
        evaluation.setStatus(EvaluationStatus.PENDING);
        return evaluationRepository.save(evaluation);
    }
    
    @Transactional
    public PerformanceEvaluation update(Long id, PerformanceEvaluation evaluation) {
        PerformanceEvaluation existingEvaluation = findById(id);
        validateEvaluation(evaluation);
        
        existingEvaluation.setEvaluationType(evaluation.getEvaluationType());
        existingEvaluation.setScore(evaluation.getScore());
        existingEvaluation.setComments(evaluation.getComments());
        
        return evaluationRepository.save(existingEvaluation);
    }
    
    @Transactional
    public PerformanceEvaluation startEvaluation(Long id) {
        PerformanceEvaluation evaluation = findById(id);
        evaluation.setStatus(EvaluationStatus.IN_PROGRESS);
        return evaluationRepository.save(evaluation);
    }
    
    @Transactional
    public PerformanceEvaluation completeEvaluation(Long id) {
        PerformanceEvaluation evaluation = findById(id);
        evaluation.setStatus(EvaluationStatus.COMPLETED);
        return evaluationRepository.save(evaluation);
    }
    
    @Transactional
    public PerformanceEvaluation reviewEvaluation(Long id) {
        PerformanceEvaluation evaluation = findById(id);
        evaluation.setStatus(EvaluationStatus.IN_PROGRESS);
        return evaluationRepository.save(evaluation);
    }
    
    @Transactional
    public PerformanceEvaluation approveEvaluation(Long id) {
        PerformanceEvaluation evaluation = findById(id);
        evaluation.setStatus(EvaluationStatus.COMPLETED);
        return evaluationRepository.save(evaluation);
    }
    
    public PerformanceEvaluation findById(Long id) {
        return evaluationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Performance evaluation not found"));
    }
    
    public List<PerformanceEvaluation> findByEmployeeId(Long employeeId) {
        return evaluationRepository.findByEmployeeId(employeeId);
    }
    
    public List<PerformanceEvaluation> findByEvaluatorId(Long evaluatorId) {
        return evaluationRepository.findByEvaluatorId(evaluatorId);
    }
    
    public List<PerformanceEvaluation> findByEmployeeIdAndStatus(Long employeeId, EvaluationStatus status) {
        return evaluationRepository.findByEmployeeIdAndStatus(employeeId, status);
    }
    
    public List<PerformanceEvaluation> findByEvaluationDateBetween(LocalDate startDate, LocalDate endDate) {
        return evaluationRepository.findByEvaluationDateBetween(startDate, endDate);
    }
    
    private void validateEvaluation(PerformanceEvaluation evaluation) {
        if (evaluation.getEvaluationDate() == null) {
            throw new IllegalArgumentException("Evaluation date is required");
        }
        
        if (evaluation.getScore() != null && (evaluation.getScore() < 0 || evaluation.getScore() > 10)) {
            throw new IllegalArgumentException("Score must be between 0 and 10");
        }
        
        if (evaluation.getEvaluationType() == null) {
            throw new IllegalArgumentException("Evaluation type is required");
        }
    }
} 