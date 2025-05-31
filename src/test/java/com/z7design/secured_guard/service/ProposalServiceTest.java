package com.z7design.secured_guard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.Proposal;
import com.z7design.secured_guard.model.enums.ProposalStatus;
import com.z7design.secured_guard.repository.ProposalRepository;

@ExtendWith(MockitoExtension.class)
class ProposalServiceTest {

    @Mock
    private ProposalRepository proposalRepository;

    @InjectMocks
    private ProposalService proposalService;

    private Proposal proposal;
    private Contract contract;
    private UUID proposalId;
    private UUID contractId;

    @BeforeEach
    void setUp() {
        proposalId = UUID.randomUUID();
        contractId = UUID.randomUUID();

        contract = Contract.builder()
                .id(contractId)
                .contractNumber("CONTRACT-001")
                .build();

        proposal = Proposal.builder()
                .id(proposalId)
                .proposalNumber("PROP-001")
                .status(ProposalStatus.RASCUNHO)
                .value(new BigDecimal("10000.00"))
                .description("Test proposal")
                .validity(LocalDateTime.now().plusDays(30))
                .contract(contract)
                .build();
    }

    @Test
    void whenCreateProposal_thenProposalIsCreated() {
        when(proposalRepository.existsByContractId(contractId)).thenReturn(false);
        when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);

        Proposal createdProposal = proposalService.create(proposal);

        assertNotNull(createdProposal);
        assertEquals(proposalId, createdProposal.getId());
        verify(proposalRepository).existsByContractId(contractId);
        verify(proposalRepository).save(proposal);
    }

    @Test
    void whenCreateProposalForExistingContract_thenThrowException() {
        when(proposalRepository.existsByContractId(contractId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            proposalService.create(proposal);
        });
    }

    @Test
    void whenUpdateProposal_thenProposalIsUpdated() {
        when(proposalRepository.findById(proposalId)).thenReturn(Optional.of(proposal));
        when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);

        Proposal updatedProposal = proposalService.update(proposalId, proposal);

        assertNotNull(updatedProposal);
        assertEquals(proposalId, updatedProposal.getId());
        verify(proposalRepository).findById(proposalId);
        verify(proposalRepository).save(proposal);
    }

    @Test
    void whenUpdateNonExistentProposal_thenThrowException() {
        when(proposalRepository.findById(proposalId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            proposalService.update(proposalId, proposal);
        });
    }

    @Test
    void whenDeleteProposal_thenProposalIsDeleted() {
        when(proposalRepository.findById(proposalId)).thenReturn(Optional.of(proposal));
        doNothing().when(proposalRepository).delete(proposal);

        proposalService.delete(proposalId);

        verify(proposalRepository).findById(proposalId);
        verify(proposalRepository).delete(proposal);
    }

    @Test
    void whenFindById_thenReturnProposal() {
        when(proposalRepository.findById(proposalId)).thenReturn(Optional.of(proposal));

        Proposal foundProposal = proposalService.findById(proposalId);

        assertNotNull(foundProposal);
        assertEquals(proposalId, foundProposal.getId());
        verify(proposalRepository).findById(proposalId);
    }

    @Test
    void whenFindByContractId_thenReturnProposal() {
        List<Proposal> proposals = Arrays.asList(proposal);
        when(proposalRepository.findByContractId(contractId)).thenReturn(proposals);

        List<Proposal> foundProposals = proposalService.findByContractId(contractId);

        assertNotNull(foundProposals);
        assertFalse(foundProposals.isEmpty());
        assertEquals(contractId, foundProposals.get(0).getContract().getId());
        verify(proposalRepository).findByContractId(contractId);
    }

    @Test
    void whenFindAll_thenReturnAllProposals() {
        List<Proposal> proposals = Arrays.asList(proposal);
        when(proposalRepository.findAll()).thenReturn(proposals);

        List<Proposal> foundProposals = proposalService.findAll();

        assertNotNull(foundProposals);
        assertEquals(1, foundProposals.size());
        verify(proposalRepository).findAll();
    }
} 