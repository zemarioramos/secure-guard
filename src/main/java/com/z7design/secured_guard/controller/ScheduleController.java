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

import com.z7design.secured_guard.model.Schedule;
import com.z7design.secured_guard.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.create(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@PathVariable UUID id, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.update(id, schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Schedule>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(scheduleService.findByEmployeeId(employeeId));
    }

    @GetMapping("/date")
    public ResponseEntity<List<Schedule>> findByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(scheduleService.findByDate(date));
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }
} 