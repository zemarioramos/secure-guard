package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.model.Document;
import com.z7design.secured_guard.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    
    private final DocumentService documentService;
    
    @PostMapping
    public ResponseEntity<Document> create(@RequestBody Document document) {
        return ResponseEntity.ok(documentService.create(document));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Document> update(@PathVariable UUID id, @RequestBody Document document) {
        return ResponseEntity.ok(documentService.update(id, document));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Document> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(documentService.findById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Document>> findByEmployeeId(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(documentService.findByEmployeeId(employeeId));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Document>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(documentService.findByType(type));
    }
    
    @GetMapping("/expiring")
    public ResponseEntity<List<Document>> findExpiringDocuments() {
        return ResponseEntity.ok(documentService.findExpiringDocuments());
    }
    
    @GetMapping
    public ResponseEntity<List<Document>> findAll() {
        return ResponseEntity.ok(documentService.findAll());
    }
} 