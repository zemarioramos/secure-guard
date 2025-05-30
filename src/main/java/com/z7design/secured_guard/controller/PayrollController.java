package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Payroll;
import com.z7design.secured_guard.service.PayrollService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayrollController {
    
    private final PayrollService payrollService;
    
    @PostMapping
    public ResponseEntity<Payroll> create(@RequestBody Payroll payroll) {
        return ResponseEntity.ok(payrollService.create(payroll));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Payroll> update(@PathVariable UUID id, @RequestBody Payroll payroll) {
        return ResponseEntity.ok(payrollService.update(id, payroll));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        payrollService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Payroll> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(payrollService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Payroll>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(payrollService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<Payroll>> findByUnitId(@PathVariable UUID unitId) {
        return ResponseEntity.ok(payrollService.findByUnitId(unitId));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Payroll>> findByDateBetween(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(payrollService.findByDateBetween(startDate, endDate));
    }
    
    @GetMapping("/month/{month}")
    public ResponseEntity<List<Payroll>> findByMonth(@PathVariable String month) {
        return ResponseEntity.ok(payrollService.findByMonth(month));
    }
    
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Payroll>> findByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(payrollService.findByYear(year));
    }
    
    @GetMapping
    public ResponseEntity<List<Payroll>> findAll() {
        return ResponseEntity.ok(payrollService.findAll());
    }
} 