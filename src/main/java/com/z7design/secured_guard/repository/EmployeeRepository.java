package com.z7design.secured_guard.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Employee;
import com.z7design.secured_guard.model.enums.EmploymentStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    
    Optional<Employee> findByCpf(String cpf);
    
    Optional<Employee> findByRg(String rg);
    
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByStatus(EmploymentStatus status);
    
    List<Employee> findByUnitId(UUID unitId);
    
    List<Employee> findByPositionId(UUID positionId);
    
    boolean existsByCpf(String cpf);
    
    boolean existsByRg(String rg);
    
    boolean existsByEmail(String email);
} 