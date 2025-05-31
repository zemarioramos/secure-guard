package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.JobVacancy;
import com.z7design.secured_guard.service.JobVacancyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job-vacancies")
@RequiredArgsConstructor
public class JobVacancyController {
    
    private final JobVacancyService jobVacancyService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<JobVacancy> create(@RequestBody JobVacancy jobVacancy) {
        return ResponseEntity.ok(jobVacancyService.create(jobVacancy));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<JobVacancy> update(@PathVariable UUID id, @RequestBody JobVacancy jobVacancy) {
        return ResponseEntity.ok(jobVacancyService.update(id, jobVacancy));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        jobVacancyService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<JobVacancy> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(jobVacancyService.findById(id));
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<JobVacancy>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(jobVacancyService.findByStatus(status));
    }
    
    @GetMapping("/department/{department}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<JobVacancy>> findByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(jobVacancyService.findByDepartment(department));
    }
    
    @GetMapping("/position/{position}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<JobVacancy>> findByPosition(@PathVariable String position) {
        return ResponseEntity.ok(jobVacancyService.findByPosition(position));
    }
    
    @GetMapping("/expired")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<List<JobVacancy>> findExpiredVacancies() {
        return ResponseEntity.ok(jobVacancyService.findExpiredVacancies());
    }
    
    @GetMapping("/status/{status}/department/{department}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<JobVacancy>> findByStatusAndDepartment(
            @PathVariable String status,
            @PathVariable String department) {
        return ResponseEntity.ok(jobVacancyService.findByStatusAndDepartment(status, department));
    }
    
    @GetMapping("/status/{status}/position/{position}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<JobVacancy>> findByStatusAndPosition(
            @PathVariable String status,
            @PathVariable String position) {
        return ResponseEntity.ok(jobVacancyService.findByStatusAndPosition(status, position));
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<JobVacancy>> findAll() {
        return ResponseEntity.ok(jobVacancyService.findAll());
    }
} 