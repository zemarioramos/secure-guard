package com.z7design.secured_guard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.PerformanceEvaluation;
import com.z7design.secured_guard.model.enums.EvaluationStatus;
import com.z7design.secured_guard.service.PerformanceEvaluationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/performance-evaluations")
@RequiredArgsConstructor
public class PerformanceEvaluationController {
    
    private final PerformanceEvaluationService evaluationService;
    
    @PostMapping
    public ResponseEntity<PerformanceEvaluation> create(@RequestBody PerformanceEvaluation evaluation) {
        return ResponseEntity.ok(evaluationService.create(evaluation));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PerformanceEvaluation> update(@PathVariable UUID id, @RequestBody PerformanceEvaluation evaluation) {
        return ResponseEntity.ok(evaluationService.update(id, evaluation));
    }
    
    @PostMapping("/{id}/start")
    public ResponseEntity<PerformanceEvaluation> startEvaluation(@PathVariable UUID id) {
        return ResponseEntity.ok(evaluationService.startEvaluation(id));
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<PerformanceEvaluation> completeEvaluation(@PathVariable UUID id) {
        return ResponseEntity.ok(evaluationService.completeEvaluation(id));
    }
    
    @PostMapping("/{id}/review")
    public ResponseEntity<PerformanceEvaluation> reviewEvaluation(@PathVariable UUID id) {
        return ResponseEntity.ok(evaluationService.reviewEvaluation(id));
    }
    
    @PostMapping("/{id}/approve")
    public ResponseEntity<PerformanceEvaluation> approveEvaluation(@PathVariable UUID id) {
        return ResponseEntity.ok(evaluationService.approveEvaluation(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PerformanceEvaluation> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(evaluationService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PerformanceEvaluation>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(evaluationService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/evaluator/{evaluatorId}")
    public ResponseEntity<List<PerformanceEvaluation>> findByEvaluatorId(@PathVariable UUID evaluatorId) {
        return ResponseEntity.ok(evaluationService.findByEvaluatorId(evaluatorId));
    }
    
    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<PerformanceEvaluation>> findByEmployeeIdAndStatus(
            @PathVariable UUID employeeId,
            @PathVariable EvaluationStatus status) {
        return ResponseEntity.ok(evaluationService.findByEmployeeIdAndStatus(employeeId, status));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<PerformanceEvaluation>> findByEvaluationDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(evaluationService.findByEvaluationDateBetween(startDate, endDate));
    }
} 