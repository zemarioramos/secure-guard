package com.z7design.secured_guard.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.dto.ProposalDTO;
import com.z7design.secured_guard.mapper.ProposalMapper;
import com.z7design.secured_guard.model.Proposal;
import com.z7design.secured_guard.model.enums.ProposalStatus;
import com.z7design.secured_guard.service.ProposalService;

@WebMvcTest(ProposalController.class)
class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProposalService proposalService;

    @MockBean
    private ProposalMapper proposalMapper;

    private Proposal proposal;
    private ProposalDTO proposalDTO;
    private UUID proposalId;
    private UUID contractId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        proposalId = UUID.randomUUID();
        contractId = UUID.randomUUID();
        now = LocalDateTime.now();
        
        proposal = new Proposal();
        proposal.setId(proposalId);
        proposal.setContractId(contractId);
        proposal.setProposalNumber("PROP-001");
        proposal.setStatus(ProposalStatus.RASCUNHO);
        proposal.setDescription("Test Proposal");
        proposal.setValidity(LocalDateTime.now().plusDays(30));
        proposal.setCreatedAt(LocalDateTime.now());
        proposal.setUpdatedAt(LocalDateTime.now());

        proposalDTO = new ProposalDTO();
        proposalDTO.setId(proposalId);
        proposalDTO.setContractId(contractId);
        proposalDTO.setProposalNumber("PROP-001");
        proposalDTO.setStatus(ProposalStatus.RASCUNHO);
        proposalDTO.setDescription("Test Proposal");
        proposalDTO.setValidity(LocalDateTime.now().plusDays(30));
        proposalDTO.setCreatedAt(LocalDateTime.now());
        proposalDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR"})
    void whenCreateProposal_thenReturnCreatedProposal() throws Exception {
        when(proposalMapper.toEntity(any(ProposalDTO.class))).thenReturn(proposal);
        when(proposalService.create(any(Proposal.class))).thenReturn(proposal);
        when(proposalMapper.toDTO(any(Proposal.class))).thenReturn(proposalDTO);

        mockMvc.perform(post("/api/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proposalDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(proposalId.toString()))
                .andExpect(jsonPath("$.proposalNumber").value("PROP-001"));

        verify(proposalMapper).toEntity(any(ProposalDTO.class));
        verify(proposalService).create(any(Proposal.class));
        verify(proposalMapper).toDTO(any(Proposal.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR"})
    void whenUpdateProposal_thenReturnUpdatedProposal() throws Exception {
        when(proposalMapper.toEntity(any(ProposalDTO.class))).thenReturn(proposal);
        when(proposalService.update(any(UUID.class), any(Proposal.class))).thenReturn(proposal);
        when(proposalMapper.toDTO(any(Proposal.class))).thenReturn(proposalDTO);

        mockMvc.perform(put("/api/proposals/{id}", proposalId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proposalDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(proposalId.toString()))
                .andExpect(jsonPath("$.proposalNumber").value("PROP-001"));

        verify(proposalMapper).toEntity(any(ProposalDTO.class));
        verify(proposalService).update(any(UUID.class), any(Proposal.class));
        verify(proposalMapper).toDTO(any(Proposal.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenDeleteProposal_thenReturnNoContent() throws Exception {
        doNothing().when(proposalService).delete(any(UUID.class));

        mockMvc.perform(delete("/api/proposals/{id}", proposalId))
                .andExpect(status().isNoContent());

        verify(proposalService).delete(proposalId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindById_thenReturnProposal() throws Exception {
        when(proposalService.findById(any(UUID.class))).thenReturn(proposal);
        when(proposalMapper.toDTO(any(Proposal.class))).thenReturn(proposalDTO);

        mockMvc.perform(get("/api/proposals/{id}", proposalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(proposalId.toString()))
                .andExpect(jsonPath("$.proposalNumber").value("PROP-001"));

        verify(proposalService).findById(proposalId);
        verify(proposalMapper).toDTO(any(Proposal.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindByContractId_thenReturnProposal() throws Exception {
        List<Proposal> proposals = Arrays.asList(proposal);
        List<ProposalDTO> proposalDTOs = Arrays.asList(proposalDTO);

        when(proposalService.findByContractId(any(UUID.class))).thenReturn(proposals);
        when(proposalMapper.toDTO(any(Proposal.class))).thenReturn(proposalDTO);

        mockMvc.perform(get("/api/proposals/contract/{contractId}", contractId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(proposalId.toString()))
                .andExpect(jsonPath("$[0].contractId").value(contractId.toString()));

        verify(proposalService).findByContractId(contractId);
        verify(proposalMapper, atLeastOnce()).toDTO(any(Proposal.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindAll_thenReturnAllProposals() throws Exception {
        List<Proposal> proposals = Arrays.asList(proposal);
        List<ProposalDTO> proposalDTOs = Arrays.asList(proposalDTO);

        when(proposalService.findAll()).thenReturn(proposals);
        when(proposalMapper.toDTO(any(Proposal.class))).thenReturn(proposalDTO);

        mockMvc.perform(get("/api/proposals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(proposalId.toString()))
                .andExpect(jsonPath("$[0].proposalNumber").value("PROP-001"));

        verify(proposalService).findAll();
        verify(proposalMapper).toDTO(any(Proposal.class));
    }
} 