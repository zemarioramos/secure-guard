package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.EPI;
import com.z7design.secured_guard.model.EPIStatus;
import com.z7design.secured_guard.service.EPIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/epis")
@RequiredArgsConstructor
public class EPIController {
    
    private final EPIService epiService;
    
    @PostMapping
    public ResponseEntity<EPI> create(@RequestBody EPI epi) {
        return ResponseEntity.ok(epiService.create(epi));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EPI> update(@PathVariable UUID id, @RequestBody EPI epi) {
        return ResponseEntity.ok(epiService.update(id, epi));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        epiService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EPI> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(epiService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EPI>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(epiService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<EPI>> findByPositionId(@PathVariable UUID positionId) {
        return ResponseEntity.ok(epiService.findByPositionId(positionId));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EPI>> findByStatus(@PathVariable EPIStatus status) {
        return ResponseEntity.ok(epiService.findByStatus(status));
    }
    
    @GetMapping("/expiring")
    public ResponseEntity<List<EPI>> findExpiringEPIs() {
        return ResponseEntity.ok(epiService.findExpiringEPIs());
    }
    
    @GetMapping
    public ResponseEntity<List<EPI>> findAll() {
        return ResponseEntity.ok(epiService.findAll());
    }
} 