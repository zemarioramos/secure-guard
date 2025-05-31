package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.dto.FilialDTO;
import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.service.FilialService;

@RestController
@RequestMapping("/api/filiais")
@CrossOrigin(origins = "*")
public class FilialController {
    
    @Autowired
    private FilialService filialService;
    
    @GetMapping
    public ResponseEntity<List<FilialDTO>> getAllFiliais() {
        return ResponseEntity.ok(filialService.getAllFiliais());
    }
    
    @GetMapping("/paginated")
    public ResponseEntity<Page<FilialDTO>> getFiliaisPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(filialService.getFiliaisPaginated(pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FilialDTO> getFilialById(@PathVariable UUID id) {
        return ResponseEntity.ok(filialService.getFilialById(id));
    }
    
    @PostMapping
    public ResponseEntity<FilialDTO> createFilial(@RequestBody FilialDTO filialDTO) {
        return ResponseEntity.ok(filialService.createFilial(filialDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FilialDTO> updateFilial(
            @PathVariable UUID id, 
            @RequestBody FilialDTO filialDTO) {
        return ResponseEntity.ok(filialService.updateFilial(id, filialDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilial(@PathVariable UUID id) {
        filialService.deleteFilial(id);
        return ResponseEntity.ok().build();
    }
}