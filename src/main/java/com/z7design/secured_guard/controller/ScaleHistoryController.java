package com.z7design.secured_guard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.ScaleHistory;
import com.z7design.secured_guard.service.ScaleHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/scale-histories")
@RequiredArgsConstructor
public class ScaleHistoryController {
    
    private final ScaleHistoryService scaleHistoryService;
    
    @PostMapping
    public ResponseEntity<ScaleHistory> create(@RequestBody ScaleHistory scaleHistory) {
        return ResponseEntity.ok(scaleHistoryService.create(scaleHistory));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ScaleHistory> update(@PathVariable UUID id, @RequestBody ScaleHistory scaleHistory) {
        return ResponseEntity.ok(scaleHistoryService.update(id, scaleHistory));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        scaleHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ScaleHistory> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(scaleHistoryService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScaleHistory>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(scaleHistoryService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<ScaleHistory>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(scaleHistoryService.findByDateRange(startDate, endDate));
    }
    
    @GetMapping("/employee/{employeeId}/date-range")
    public ResponseEntity<List<ScaleHistory>> findByEmployeeIdAndDateRange(
            @PathVariable UUID employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(scaleHistoryService.findByEmployeeIdAndDateRange(employeeId, startDate, endDate));
    }
    
    @GetMapping
    public ResponseEntity<List<ScaleHistory>> findAll() {
        return ResponseEntity.ok(scaleHistoryService.findAll());
    }
} 