package com.z7design.secured_guard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.z7design.secured_guard.model.enums.ProposalStatus;

class ProposalTest {

    private Proposal proposal;
    private Contract contract;

    @BeforeEach
    void setUp() {
        contract = Contract.builder()
                .id(UUID.randomUUID())
                .contractNumber("CONTRACT-001")
                .build();

        proposal = Proposal.builder()
                .id(UUID.randomUUID())
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
        assertNotNull(proposal);
        assertEquals("PROP-001", proposal.getProposalNumber());
        assertEquals(ProposalStatus.RASCUNHO, proposal.getStatus());
        assertEquals(new BigDecimal("10000.00"), proposal.getValue());
        assertEquals("Test proposal", proposal.getDescription());
        assertEquals(contract, proposal.getContract());
    }

    @Test
    void whenUpdateProposalStatus_thenStatusIsUpdated() {
        proposal.setStatus(ProposalStatus.ENVIADA);
        assertEquals(ProposalStatus.ENVIADA, proposal.getStatus());
    }

    @Test
    void whenUpdateProposalValue_thenValueIsUpdated() {
        proposal.setValue(new BigDecimal("15000.00"));
        assertEquals(new BigDecimal("15000.00"), proposal.getValue());
    }

    @Test
    void whenUpdateProposalDescription_thenDescriptionIsUpdated() {
        proposal.setDescription("Updated test proposal");
        assertEquals("Updated test proposal", proposal.getDescription());
    }

    @Test
    void whenUpdateProposalValidity_thenValidityIsUpdated() {
        LocalDateTime newValidity = LocalDateTime.now().plusDays(60);
        proposal.setValidity(newValidity);
        assertEquals(newValidity, proposal.getValidity());
    }

    @Test
    void whenUpdateProposalContract_thenContractIsUpdated() {
        Contract newContract = Contract.builder()
                .id(UUID.randomUUID())
                .contractNumber("CONTRACT-002")
                .build();
        
        proposal.setContract(newContract);
        assertEquals(newContract, proposal.getContract());
    }
} 