package com.z7design.secured_guard.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.TimeRecord;
import com.z7design.secured_guard.model.enums.TimeRecordStatus;
import com.z7design.secured_guard.service.TimeRecordService;

@RestController
@RequestMapping("/api/time-records")
public class TimeRecordController {

    @Autowired
    private TimeRecordService timeRecordService;

    @PostMapping
    public ResponseEntity<TimeRecord> create(@RequestBody TimeRecord timeRecord) {
        return ResponseEntity.ok(timeRecordService.create(timeRecord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeRecord> update(@PathVariable UUID id, @RequestBody TimeRecord timeRecord) {
        return ResponseEntity.ok(timeRecordService.update(id, timeRecord));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<TimeRecord> approve(@PathVariable UUID id) {
        return ResponseEntity.ok(timeRecordService.approve(id));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<TimeRecord> reject(@PathVariable UUID id, @RequestParam String justification) {
        return ResponseEntity.ok(timeRecordService.reject(id, justification));
    }

    @PostMapping("/{id}/adjust")
    public ResponseEntity<TimeRecord> adjust(
            @PathVariable UUID id,
            @RequestParam LocalTime entryTime,
            @RequestParam LocalTime exitTime,
            @RequestParam(required = false) LocalTime entryLunchTime,
            @RequestParam(required = false) LocalTime exitLunchTime,
            @RequestParam String justification) {
        return ResponseEntity.ok(timeRecordService.adjust(id, entryTime, exitTime, entryLunchTime, exitLunchTime, justification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeRecord> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(timeRecordService.findById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TimeRecord>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(timeRecordService.findByEmployeeId(employeeId));
    }

    @GetMapping("/employee/{employeeId}/period")
    public ResponseEntity<List<TimeRecord>> findByEmployeeIdAndPeriod(
            @PathVariable UUID employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(timeRecordService.findByEmployeeIdAndRecordDateBetween(employeeId, startDate, endDate));
    }

    @GetMapping("/employee/{employeeId}/status")
    public ResponseEntity<List<TimeRecord>> findByEmployeeIdAndStatus(
            @PathVariable UUID employeeId,
            @RequestParam String status) {
        return ResponseEntity.ok(timeRecordService.findByEmployeeIdAndStatus(employeeId, TimeRecordStatus.valueOf(status)));
    }
} 