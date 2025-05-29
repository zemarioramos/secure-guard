package com.z7design.secured_guard.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
    
    List<Position> findByUnitId(UUID unitId);
    
    Optional<Position> findByName(String name);
    
    boolean existsByName(String name);
    
    List<Position> findByBaseSalaryGreaterThanEqual(Double baseSalary);
} 