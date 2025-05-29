package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Employee;
import com.z7design.secured_guard.model.enums.EmploymentStatus;
import com.z7design.secured_guard.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.create(employee));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable UUID id, @Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Employee> findByCpf(@PathVariable String cpf) {
        return employeeService.findByCpf(cpf)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> findByEmail(@PathVariable String email) {
        return employeeService.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Employee>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(employeeService.findByStatus(EmploymentStatus.valueOf(status)));
    }
    
    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<Employee>> findByUnit(@PathVariable UUID unitId) {
        return ResponseEntity.ok(employeeService.findByUnit(unitId));
    }
    
    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<Employee>> findByPosition(@PathVariable UUID positionId) {
        return ResponseEntity.ok(employeeService.findByPosition(positionId));
    }
    
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Employee> updateStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        return ResponseEntity.ok(employeeService.updateStatus(id, EmploymentStatus.valueOf(status)));
    }
} 