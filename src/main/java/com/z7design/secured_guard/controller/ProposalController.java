package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.dto.ProposalDTO;
import com.z7design.secured_guard.mapper.ProposalMapper;
import com.z7design.secured_guard.model.Proposal;
import com.z7design.secured_guard.service.ProposalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proposals")
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;
    private final ProposalMapper proposalMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<ProposalDTO> create(@RequestBody ProposalDTO proposalDTO) {
        Proposal proposal = proposalMapper.toEntity(proposalDTO);
        Proposal createdProposal = proposalService.create(proposal);
        return ResponseEntity.ok(proposalMapper.toDTO(createdProposal));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<ProposalDTO> update(@PathVariable UUID id, @RequestBody ProposalDTO proposalDTO) {
        Proposal proposal = proposalMapper.toEntity(proposalDTO);
        Proposal updatedProposal = proposalService.update(id, proposal);
        return ResponseEntity.ok(proposalMapper.toDTO(updatedProposal));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        proposalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<ProposalDTO> findById(@PathVariable UUID id) {
        Proposal proposal = proposalService.findById(id);
        return ResponseEntity.ok(proposalMapper.toDTO(proposal));
    }

    @GetMapping("/contract/{contractId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ProposalDTO>> findByContractId(@PathVariable UUID contractId) {
        List<Proposal> proposals = proposalService.findByContractId(contractId);
        List<ProposalDTO> proposalDTOs = proposals.stream()
                .map(proposalMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proposalDTOs);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ProposalDTO>> findAll() {
        List<Proposal> proposals = proposalService.findAll();
        List<ProposalDTO> proposalDTOs = proposals.stream()
                .map(proposalMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proposalDTOs);
    }
} 