package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Position;
import com.z7design.secured_guard.service.PositionService;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<Position> create(@RequestBody Position position) {
        return ResponseEntity.ok(positionService.save(position));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> update(@PathVariable UUID id, @RequestBody Position position) {
        position.setId(id);
        return ResponseEntity.ok(positionService.save(position));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        positionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(positionService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Position> findByName(@PathVariable String name) {
        return positionService.findByName(name)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/salary/{baseSalary}")
    public ResponseEntity<List<Position>> findByBaseSalaryGreaterThanEqual(
            @PathVariable Double baseSalary) {
        return ResponseEntity.ok(positionService.findByBaseSalaryGreaterThanEqual(baseSalary));
    }

    @GetMapping
    public ResponseEntity<List<Position>> findAll() {
        return ResponseEntity.ok(positionService.findAll());
    }
} 