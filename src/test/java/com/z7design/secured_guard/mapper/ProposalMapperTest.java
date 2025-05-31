package com.z7design.secured_guard.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.z7design.secured_guard.dto.ProposalDTO;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.Proposal;
import com.z7design.secured_guard.model.enums.ProposalStatus;

class ProposalMapperTest {

    private ProposalMapper proposalMapper;
    private Proposal proposal;
    private ProposalDTO proposalDTO;
    private Contract contract;
    private UUID proposalId;
    private UUID contractId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        proposalMapper = new ProposalMapper();
        proposalId = UUID.randomUUID();
        contractId = UUID.randomUUID();
        now = LocalDateTime.now();

        contract = Contract.builder()
                .id(contractId)
                .build();

        proposal = Proposal.builder()
                .id(proposalId)
                .proposalNumber("PROP-001")
                .status(ProposalStatus.RASCUNHO)
                .value(new BigDecimal("1000.00"))
                .description("Test Proposal")
                .validity(now.plusDays(30))
                .contract(contract)
                .createdAt(now)
                .updatedAt(now)
                .build();

        proposalDTO = new ProposalDTO();
        proposalDTO.setId(proposalId);
        proposalDTO.setProposalNumber("PROP-001");
        proposalDTO.setStatus(ProposalStatus.RASCUNHO);
        proposalDTO.setValue(new BigDecimal("1000.00"));
        proposalDTO.setDescription("Test Proposal");
        proposalDTO.setValidity(now.plusDays(30));
        proposalDTO.setContractId(contractId);
        proposalDTO.setCreatedAt(now);
        proposalDTO.setUpdatedAt(now);
    }

    @Test
    void whenToDTO_thenReturnProposalDTO() {
        ProposalDTO result = proposalMapper.toDTO(proposal);

        assertNotNull(result);
        assertEquals(proposalId, result.getId());
        assertEquals("PROP-001", result.getProposalNumber());
        assertEquals(ProposalStatus.RASCUNHO, result.getStatus());
        assertEquals(new BigDecimal("1000.00"), result.getValue());
        assertEquals("Test Proposal", result.getDescription());
        assertEquals(now.plusDays(30), result.getValidity());
        assertEquals(contractId, result.getContractId());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
    }

    @Test
    void whenToDTOWithNull_thenReturnNull() {
        ProposalDTO result = proposalMapper.toDTO(null);
        assertNull(result);
    }

    @Test
    void whenToEntity_thenReturnProposal() {
        Proposal result = proposalMapper.toEntity(proposalDTO);

        assertNotNull(result);
        assertEquals(proposalId, result.getId());
        assertEquals("PROP-001", result.getProposalNumber());
        assertEquals(ProposalStatus.RASCUNHO, result.getStatus());
        assertEquals(new BigDecimal("1000.00"), result.getValue());
        assertEquals("Test Proposal", result.getDescription());
        assertEquals(now.plusDays(30), result.getValidity());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
    }

    @Test
    void whenToEntityWithNull_thenReturnNull() {
        Proposal result = proposalMapper.toEntity(null);
        assertNull(result);
    }
} 