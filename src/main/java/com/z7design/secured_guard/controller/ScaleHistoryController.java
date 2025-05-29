package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

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
    
    @GetMapping("/scale/{scale}")
    public ResponseEntity<List<ScaleHistory>> findByScale(@PathVariable String scale) {
        return ResponseEntity.ok(scaleHistoryService.findByScale(scale));
    }
    
    @GetMapping("/current")
    public ResponseEntity<List<ScaleHistory>> findCurrentScales() {
        return ResponseEntity.ok(scaleHistoryService.findCurrentScales());
    }
    
    @GetMapping
    public ResponseEntity<List<ScaleHistory>> findAll() {
        return ResponseEntity.ok(scaleHistoryService.findAll());
    }
} 