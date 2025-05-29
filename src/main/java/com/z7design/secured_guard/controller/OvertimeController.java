package com.z7design.secured_guard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Overtime;
import com.z7design.secured_guard.model.enums.OvertimeStatus;
import com.z7design.secured_guard.model.enums.OvertimeType;
import com.z7design.secured_guard.service.OvertimeService;

@RestController
@RequestMapping("/api/overtimes")
public class OvertimeController {

    @Autowired
    private OvertimeService overtimeService;

    @PostMapping
    public ResponseEntity<Overtime> create(@RequestBody Overtime overtime) {
        return ResponseEntity.ok(overtimeService.create(overtime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Overtime> update(@PathVariable UUID id, @RequestBody Overtime overtime) {
        return ResponseEntity.ok(overtimeService.update(id, overtime));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Overtime> approve(@PathVariable UUID id, @RequestParam UUID approvedBy) {
        return ResponseEntity.ok(overtimeService.approve(id, approvedBy));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Overtime> reject(@PathVariable UUID id, @RequestParam String justification) {
        return ResponseEntity.ok(overtimeService.reject(id, justification));
    }

    @PostMapping("/{id}/compensate")
    public ResponseEntity<Overtime> compensate(@PathVariable UUID id) {
        return ResponseEntity.ok(overtimeService.compensate(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Overtime> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(overtimeService.findById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Overtime>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(overtimeService.findByEmployeeId(employeeId));
    }

    @GetMapping("/employee/{employeeId}/status")
    public ResponseEntity<List<Overtime>> findByEmployeeIdAndStatus(
            @PathVariable UUID employeeId,
            @RequestParam String status) {
        return ResponseEntity.ok(overtimeService.findByEmployeeIdAndStatus(employeeId, OvertimeStatus.valueOf(status)));
    }

    @GetMapping("/employee/{employeeId}/type")
    public ResponseEntity<List<Overtime>> findByEmployeeIdAndType(
            @PathVariable UUID employeeId,
            @RequestParam String type) {
        return ResponseEntity.ok(overtimeService.findByEmployeeIdAndType(employeeId, OvertimeType.valueOf(type)));
    }

    @GetMapping("/employee/{employeeId}/period")
    public ResponseEntity<List<Overtime>> findByEmployeeIdAndPeriod(
            @PathVariable UUID employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(overtimeService.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate));
    }
} 