package com.z7design.secured_guard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.z7design.secured_guard.model.Vacation;
import com.z7design.secured_guard.model.enums.VacationStatus;
import com.z7design.secured_guard.service.VacationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationController {
    
    private final VacationService vacationService;
    
    @PostMapping
    public ResponseEntity<Vacation> create(@RequestBody Vacation vacation) {
        return ResponseEntity.ok(vacationService.create(vacation));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vacation> update(@PathVariable UUID id, @RequestBody Vacation vacation) {
        return ResponseEntity.ok(vacationService.update(id, vacation));
    }
    
    @PutMapping("/{id}/approve")
    public ResponseEntity<Vacation> approve(@PathVariable UUID id, @RequestParam UUID approvedBy) {
        return ResponseEntity.ok(vacationService.approve(id, approvedBy));
    }
    
    @PutMapping("/{id}/reject")
    public ResponseEntity<Vacation> reject(@PathVariable UUID id) {
        return ResponseEntity.ok(vacationService.reject(id));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Vacation> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(vacationService.cancel(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        vacationService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vacation> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(vacationService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Vacation>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(vacationService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<Vacation>> findByEmployeeIdAndStatus(
            @PathVariable UUID employeeId,
            @PathVariable VacationStatus status) {
        return ResponseEntity.ok(vacationService.findByEmployeeIdAndStatus(employeeId, status));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Vacation>> findByStartDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(vacationService.findByStartDateBetween(startDate, endDate));
    }
    
    @GetMapping
    public ResponseEntity<List<Vacation>> findAll() {
        return ResponseEntity.ok(vacationService.findAll());
    }
} 