package com.z7design.secured_guard.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.model.enums.ContractType;

class ContractTest {

    private Contract contract;
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
                .contractNumber("C-12345")
                .type(ContractType.VIGILANCIA_PATRIMONIAL)
                .value(new BigDecimal("1000.00"))
                .status(ContractStatus.ATIVO)
                .startDate(now)
                .endDate(now.plusYears(1))
                .clientId(clientId)
                .unitId(unitId)
                .client(client)
                .unit(unit)
                .infrastructureDemand("Test infrastructure demand")
                .description("Test contract description")
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    @Test
    void whenCreateContract_thenContractIsCreated() {
        assertNotNull(contract);
        assertEquals(contractId, contract.getId());
        assertEquals("C-12345", contract.getContractNumber());
        assertEquals(ContractType.VIGILANCIA_PATRIMONIAL, contract.getType());
        assertEquals(new BigDecimal("1000.00"), contract.getValue());
        assertEquals(ContractStatus.ATIVO, contract.getStatus());
        assertEquals(client, contract.getClient());
        assertEquals(unit, contract.getUnit());
        assertEquals("Test infrastructure demand", contract.getInfrastructureDemand());
        assertEquals("Test contract description", contract.getDescription());
        assertEquals(now, contract.getCreatedAt());
        assertEquals(now, contract.getUpdatedAt());
        assertEquals(now, contract.getStartDate());
        assertEquals(now.plusYears(1), contract.getEndDate());
    }

    @Test
    void whenUpdateContract_thenContractIsUpdated() {
        contract.setType(ContractType.VIGILANCIA_PESSOAL);
        contract.setValue(new BigDecimal("2000.00"));
        contract.setStatus(ContractStatus.SUSPENSO);
        contract.setInfrastructureDemand("Updated infrastructure demand");
        contract.setDescription("Updated contract description");

        assertEquals(ContractType.VIGILANCIA_PESSOAL, contract.getType());
        assertEquals(new BigDecimal("2000.00"), contract.getValue());
        assertEquals(ContractStatus.SUSPENSO, contract.getStatus());
        assertEquals("Updated infrastructure demand", contract.getInfrastructureDemand());
        assertEquals("Updated contract description", contract.getDescription());
    }

    @Test
    void whenSetTimestamps_thenTimestampsAreSet() {
        LocalDateTime createdAt = now.minusDays(1);
        LocalDateTime updatedAt = now;

        contract.setCreatedAt(createdAt);
        contract.setUpdatedAt(updatedAt);

        assertEquals(createdAt, contract.getCreatedAt());
        assertEquals(updatedAt, contract.getUpdatedAt());
    }

    @Test
    void whenSetId_thenIdIsSet() {
        UUID newId = UUID.randomUUID();
        contract.setId(newId);
        assertEquals(newId, contract.getId());
    }
} 