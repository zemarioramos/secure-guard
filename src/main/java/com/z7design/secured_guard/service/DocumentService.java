package com.z7design.secured_guard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.model.Document;
import com.z7design.secured_guard.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DocumentService {
    
    private final DocumentRepository documentRepository;
    
    @Transactional
    public Document create(Document document) {
        return documentRepository.save(document);
    }
    
    @Transactional
    public Document update(UUID id, Document document) {
        Document existingDocument = findById(id);
        document.setId(id);
        return documentRepository.save(document);
    }
    
    @Transactional
    public void delete(UUID id) {
        Document document = findById(id);
        documentRepository.delete(document);
    }
    
    public Document findById(UUID id) {
        return documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
    }
    
    public List<Document> findByEmployeeId(UUID employeeId) {
        return documentRepository.findByEmployeeId(employeeId);
    }
    
    public List<Document> findByType(String type) {
        return documentRepository.findByType(type);
    }
    
    public List<Document> findExpiredDocuments() {
        return documentRepository.findByExpirationDateBefore(LocalDateTime.now());
    }
    
    public List<Document> findByEmployeeIdAndType(UUID employeeId, String type) {
        return documentRepository.findByEmployeeIdAndType(employeeId, type);
    }
    
    public List<Document> findAll() {
        return documentRepository.findAll();
    }
    
    public List<Document> findExpiringDocuments() {
        LocalDateTime threeMonthsFromNow = LocalDateTime.now().plusMonths(3);
        return documentRepository.findByExpirationDateBefore(threeMonthsFromNow);
    }
} 