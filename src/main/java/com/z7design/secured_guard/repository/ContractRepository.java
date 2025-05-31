package com.z7design.secured_guard.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.enums.ContractStatus;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {
    List<Contract> findByStatus(ContractStatus status);
    List<Contract> findByUnitId(UUID unitId);
    List<Contract> findByClientId(UUID clientId);
    List<Contract> findByStatusAndUnitId(ContractStatus status, UUID unitId);
} 