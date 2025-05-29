package com.z7design.secured_guard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.z7design.secured_guard.model.Leave;
import com.z7design.secured_guard.model.enums.LeaveStatus;
import com.z7design.secured_guard.model.enums.LeaveType;
import com.z7design.secured_guard.service.LeaveService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {
    
    private final LeaveService leaveService;
    
    @PostMapping
    public ResponseEntity<Leave> create(@RequestBody Leave leave) {
        return ResponseEntity.ok(leaveService.create(leave));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Leave> update(@PathVariable UUID id, @RequestBody Leave leave) {
        return ResponseEntity.ok(leaveService.update(id, leave));
    }
    
    @PutMapping("/{id}/approve")
    public ResponseEntity<Leave> approve(
            @PathVariable UUID id,
            @RequestParam UUID approvedBy) {
        return ResponseEntity.ok(leaveService.approve(id, approvedBy));
    }
    
    @PutMapping("/{id}/reject")
    public ResponseEntity<Leave> reject(@PathVariable UUID id, @RequestParam String justification) {
        return ResponseEntity.ok(leaveService.reject(id, justification));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Leave> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveService.cancel(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Leave> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Leave>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(leaveService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<Leave>> findByEmployeeIdAndStatus(
            @PathVariable UUID employeeId,
            @PathVariable LeaveStatus status) {
        return ResponseEntity.ok(leaveService.findByEmployeeIdAndStatus(employeeId, status));
    }
    
    @GetMapping("/employee/{employeeId}/type/{leaveType}")
    public ResponseEntity<List<Leave>> findByEmployeeIdAndLeaveType(
            @PathVariable UUID employeeId,
            @PathVariable LeaveType leaveType) {
        return ResponseEntity.ok(leaveService.findByEmployeeIdAndLeaveType(employeeId, leaveType));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Leave>> findByStartDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(leaveService.findByStartDateBetween(startDate, endDate));
    }
} 