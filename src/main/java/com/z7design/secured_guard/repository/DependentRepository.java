package com.z7design.secured_guard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Dependent;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Long> {
    List<Dependent> findByEmployeeId(Long employeeId);
    List<Dependent> findByCpf(String cpf);
    List<Dependent> findByRelationship(String relationship);
} 