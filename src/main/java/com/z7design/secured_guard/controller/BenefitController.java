package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Benefit;
import com.z7design.secured_guard.service.BenefitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/benefits")
@RequiredArgsConstructor
public class BenefitController {
    
    private final BenefitService benefitService;
    
    @PostMapping
    public ResponseEntity<Benefit> create(@RequestBody Benefit benefit) {
        return ResponseEntity.ok(benefitService.create(benefit));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Benefit> update(@PathVariable UUID id, @RequestBody Benefit benefit) {
        return ResponseEntity.ok(benefitService.update(id, benefit));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        benefitService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Benefit> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(benefitService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Benefit>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(benefitService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<Benefit>> findByPositionId(@PathVariable UUID positionId) {
        return ResponseEntity.ok(benefitService.findByPositionId(positionId));
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Benefit>> findActiveBenefits() {
        return ResponseEntity.ok(benefitService.findActiveBenefits());
    }
    
    @GetMapping
    public ResponseEntity<List<Benefit>> findAll() {
        return ResponseEntity.ok(benefitService.findAll());
    }
} 