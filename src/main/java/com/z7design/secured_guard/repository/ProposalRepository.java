package com.z7design.secured_guard.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Proposal;
import com.z7design.secured_guard.model.enums.ProposalStatus;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, UUID> {
    List<Proposal> findByContractId(UUID contractId);
    List<Proposal> findByStatus(ProposalStatus status);
    boolean existsByContractId(UUID contractId);
} 