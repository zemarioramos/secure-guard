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

import com.z7design.secured_guard.model.Escala;
import com.z7design.secured_guard.service.EscalaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/escalas")
@RequiredArgsConstructor
public class EscalaController {

    private final EscalaService escalaService;

    @PostMapping
    public ResponseEntity<Escala> create(@RequestBody Escala escala) {
        return ResponseEntity.ok(escalaService.create(escala));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Escala> update(@PathVariable UUID id, @RequestBody Escala escala) {
        return ResponseEntity.ok(escalaService.update(id, escala));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        escalaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Escala> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(escalaService.findById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Escala>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(escalaService.findByEmployeeId(employeeId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Escala>> findByDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(escalaService.findByDateBetween(startDate, endDate));
    }

    @GetMapping
    public ResponseEntity<List<Escala>> findAll() {
        return ResponseEntity.ok(escalaService.findAll());
    }
} 