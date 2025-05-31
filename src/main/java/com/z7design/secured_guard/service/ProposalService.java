package com.z7design.secured_guard.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.model.Proposal;
import com.z7design.secured_guard.repository.ProposalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProposalService {
    
    private final ProposalRepository proposalRepository;
    
    @Transactional
    public Proposal create(Proposal proposal) {
        if (proposalRepository.existsByContractId(proposal.getContract().getId())) {
            throw new RuntimeException("Já existe uma proposta para este contrato");
        }
        return proposalRepository.save(proposal);
    }
    
    @Transactional
    public Proposal update(UUID id, Proposal proposal) {
        Proposal existingProposal = findById(id);
        proposal.setId(id);
        return proposalRepository.save(proposal);
    }
    
    @Transactional
    public void delete(UUID id) {
        Proposal proposal = findById(id);
        proposalRepository.delete(proposal);
    }
    
    public Proposal findById(UUID id) {
        return proposalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));
    }
    
    public List<Proposal> findByContractId(UUID contractId) {
        List<Proposal> proposals = proposalRepository.findByContractId(contractId);
        if (proposals.isEmpty()) {
            throw new RuntimeException("Nenhuma proposta encontrada para este contrato");
        }
        return proposals;
    }
    
    public List<Proposal> findAll() {
        return proposalRepository.findAll();
    }
} 