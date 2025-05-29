package com.z7design.secured_guard.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {
    
    Optional<Unit> findByName(String name);
    
    Optional<Unit> findByEmail(String email);
    
    List<Unit> findByAddressContaining(String address);
    
    List<Unit> findByParentId(UUID parentId);
    
    boolean existsByName(String name);
    
    boolean existsByEmail(String email);
} 