package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Occurrence;
import com.z7design.secured_guard.model.OccurrenceType;
import com.z7design.secured_guard.service.OccurrenceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/occurrences")
@RequiredArgsConstructor
public class OccurrenceController {
    
    private final OccurrenceService occurrenceService;
    
    @PostMapping
    public ResponseEntity<Occurrence> create(@RequestBody Occurrence occurrence) {
        return ResponseEntity.ok(occurrenceService.create(occurrence));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Occurrence> update(@PathVariable UUID id, @RequestBody Occurrence occurrence) {
        return ResponseEntity.ok(occurrenceService.update(id, occurrence));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        occurrenceService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Occurrence> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(occurrenceService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Occurrence>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(occurrenceService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Occurrence>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(occurrenceService.findByType(OccurrenceType.valueOf(type.toUpperCase())));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Occurrence>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(occurrenceService.findByStatus(status));
    }
    
    @GetMapping
    public ResponseEntity<List<Occurrence>> findAll() {
        return ResponseEntity.ok(occurrenceService.findAll());
    }
} 