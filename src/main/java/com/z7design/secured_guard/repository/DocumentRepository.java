package com.z7design.secured_guard.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    
    List<Document> findByEmployeeId(UUID employeeId);
    
    List<Document> findByType(String type);
    
    List<Document> findByExpirationDateBefore(LocalDateTime date);
    
    List<Document> findByEmployeeIdAndType(UUID employeeId, String type);
} 