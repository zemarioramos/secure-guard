package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.service.UnitService;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    
    @Autowired
    private UnitService unitService;
    
    @PostMapping
    public ResponseEntity<Unit> create(@RequestBody Unit unit) {
        return ResponseEntity.ok(unitService.create(unit));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Unit> update(@PathVariable UUID id, @RequestBody Unit unit) {
        return ResponseEntity.ok(unitService.update(id, unit));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        unitService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Unit> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(unitService.findById(id));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Unit> findByName(@PathVariable String name) {
        return ResponseEntity.ok(unitService.findByName(name));
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Unit> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(unitService.findByEmail(email));
    }
    
    @GetMapping("/address/{address}")
    public ResponseEntity<List<Unit>> findByAddressContaining(@PathVariable String address) {
        return ResponseEntity.ok(unitService.findByAddressContaining(address));
    }
    
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Unit>> findByParentId(@PathVariable UUID parentId) {
        return ResponseEntity.ok(unitService.findByParentId(parentId));
    }
    
    @GetMapping
    public ResponseEntity<List<Unit>> findAll() {
        return ResponseEntity.ok(unitService.findAll());
    }
} 