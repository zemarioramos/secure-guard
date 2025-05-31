package com.z7design.secured_guard.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.z7design.secured_guard.dto.ContractDTO;
import com.z7design.secured_guard.mapper.ContractMapper;
import com.z7design.secured_guard.model.Client;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.model.enums.ContractType;
import com.z7design.secured_guard.service.ContractService;

@WebMvcTest(ContractController.class)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContractService contractService;

    @MockBean
    private ContractMapper contractMapper;

    private Contract contract;
    private ContractDTO contractDTO;
    private Client client;
    private Unit unit;
    private UUID contractId;
    private UUID clientId;
    private UUID unitId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        contractId = UUID.randomUUID();
        clientId = UUID.randomUUID();
        unitId = UUID.randomUUID();
        now = LocalDateTime.now();

        client = new Client();
        client.setId(clientId);
        client.setCompanyName("Test Company");

        unit = Unit.builder()
                .id(unitId)
                .name("Test Unit")
                .build();

        contract = Contract.builder()
                .id(contractId)
                .serviceType(ContractType.VIGILANCIA_PATRIMONIAL.name())
                .value(new BigDecimal("10000.00"))
                .status(ContractStatus.ATIVO.name())
                .validity(LocalDate.now().plusYears(1))
                .client(client)
                .unit(unit)
                .infrastructureDemand("Test infrastructure demand")
                .createdAt(now)
                .updatedAt(now)
                .build();

        contractDTO = new ContractDTO();
        contractDTO.setId(contractId);
        contractDTO.setType(ContractType.VIGILANCIA_PATRIMONIAL);
        contractDTO.setStatus(ContractStatus.ATIVO);
        contractDTO.setValue(new BigDecimal("10000.00"));
        contractDTO.setClientId(clientId);
        contractDTO.setUnitId(unitId);
        contractDTO.setCreatedAt(now);
        contractDTO.setUpdatedAt(now);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenCreateContract_thenReturnCreatedContract() throws Exception {
        when(contractMapper.toEntity(any(ContractDTO.class))).thenReturn(contract);
        when(contractService.create(any(Contract.class))).thenReturn(contract);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(post("/api/contracts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contractDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contractId.toString()))
                .andExpect(jsonPath("$.type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractMapper).toEntity(any(ContractDTO.class));
        verify(contractService).create(any(Contract.class));
        verify(contractMapper).toDTO(any(Contract.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenUpdateContract_thenReturnUpdatedContract() throws Exception {
        when(contractMapper.toEntity(any(ContractDTO.class))).thenReturn(contract);
        when(contractService.update(any(UUID.class), any(Contract.class))).thenReturn(contract);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(put("/api/contracts/{id}", contractId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contractDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contractId.toString()))
                .andExpect(jsonPath("$.type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractMapper).toEntity(any(ContractDTO.class));
        verify(contractService).update(any(UUID.class), any(Contract.class));
        verify(contractMapper).toDTO(any(Contract.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenDeleteContract_thenReturnNoContent() throws Exception {
        doNothing().when(contractService).delete(any(UUID.class));

        mockMvc.perform(delete("/api/contracts/{id}", contractId))
                .andExpect(status().isNoContent());

        verify(contractService).delete(contractId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindById_thenReturnContract() throws Exception {
        when(contractService.findById(any(UUID.class))).thenReturn(contract);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(get("/api/contracts/{id}", contractId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contractId.toString()))
                .andExpect(jsonPath("$.type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractService).findById(contractId);
        verify(contractMapper).toDTO(any(Contract.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindByStatus_thenReturnContracts() throws Exception {
        List<Contract> contracts = Arrays.asList(contract);
        List<ContractDTO> contractDTOs = Arrays.asList(contractDTO);

        when(contractService.findByStatus(anyString())).thenReturn(contracts);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(get("/api/contracts/status/{status}", ContractStatus.ATIVO.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contractId.toString()))
                .andExpect(jsonPath("$[0].type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractService).findByStatus(ContractStatus.ATIVO.name());
        verify(contractMapper).toDTO(any(Contract.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindByUnit_thenReturnContracts() throws Exception {
        List<Contract> contracts = Arrays.asList(contract);
        List<ContractDTO> contractDTOs = Arrays.asList(contractDTO);

        when(contractService.findByUnit(any(UUID.class))).thenReturn(contracts);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(get("/api/contracts/unit/{unitId}", unitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contractId.toString()))
                .andExpect(jsonPath("$[0].type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractService).findByUnit(unitId);
        verify(contractMapper).toDTO(any(Contract.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindByClient_thenReturnContracts() throws Exception {
        List<Contract> contracts = Arrays.asList(contract);
        List<ContractDTO> contractDTOs = Arrays.asList(contractDTO);

        when(contractService.findByClient(any(UUID.class))).thenReturn(contracts);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(get("/api/contracts/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contractId.toString()))
                .andExpect(jsonPath("$[0].type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractService).findByClient(clientId);
        verify(contractMapper).toDTO(any(Contract.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindByStatusAndUnit_thenReturnContracts() throws Exception {
        List<Contract> contracts = Arrays.asList(contract);
        List<ContractDTO> contractDTOs = Arrays.asList(contractDTO);

        when(contractService.findByStatusAndUnit(anyString(), any(UUID.class))).thenReturn(contracts);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(get("/api/contracts/status/{status}/unit/{unitId}", ContractStatus.ATIVO.name(), unitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contractId.toString()))
                .andExpect(jsonPath("$[0].type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractService).findByStatusAndUnit(ContractStatus.ATIVO.name(), unitId);
        verify(contractMapper).toDTO(any(Contract.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindAll_thenReturnAllContracts() throws Exception {
        List<Contract> contracts = Arrays.asList(contract);
        List<ContractDTO> contractDTOs = Arrays.asList(contractDTO);

        when(contractService.findAll()).thenReturn(contracts);
        when(contractMapper.toDTO(any(Contract.class))).thenReturn(contractDTO);

        mockMvc.perform(get("/api/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(contractId.toString()))
                .andExpect(jsonPath("$[0].type").value(ContractType.VIGILANCIA_PATRIMONIAL.name()));

        verify(contractService).findAll();
        verify(contractMapper).toDTO(any(Contract.class));
    }
} 