package com.z7design.secured_guard.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.z7design.secured_guard.dto.ContractDTO;
import com.z7design.secured_guard.model.Client;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.model.enums.ContractType;

class ContractMapperTest {

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
        contractMapper = new ContractMapper();
        contractId = UUID.randomUUID();
        clientId = UUID.randomUUID();
        unitId = UUID.randomUUID();
        now = LocalDateTime.now();

        client = new Client();
        client.setId(clientId);
        client.setCompanyName("Test Company");

        unit = new Unit();
        unit.setId(unitId);
        unit.setName("Test Unit");

        contract = new Contract();
        contract.setId(contractId);
        contract.setType(ContractType.VIGILANCIA_PATRIMONIAL);
        contract.setValue(new BigDecimal("1000.00"));
        contract.setStatus(ContractStatus.ATIVO);
        contract.setClient(client);
        contract.setUnit(unit);
        contract.setInfrastructureDemand("Test infrastructure demand");
        contract.setCreatedAt(now);
        contract.setUpdatedAt(now);

        contractDTO = new ContractDTO();
        contractDTO.setId(contractId);
        contractDTO.setContractNumber("CONT-001");
        contractDTO.setType(ContractType.VIGILANCIA_PATRIMONIAL);
        contractDTO.setStatus(ContractStatus.ATIVO);
        contractDTO.setStartDate(now);
        contractDTO.setEndDate(now.plusYears(1));
        contractDTO.setValue(new BigDecimal("1000.00"));
        contractDTO.setDescription("Test Contract");
        contractDTO.setClientId(clientId);
        contractDTO.setUnitId(unitId);
        contractDTO.setCreatedAt(now);
        contractDTO.setUpdatedAt(now);
    }

    @Test
    void whenToDTO_thenReturnContractDTO() {
        ContractDTO result = contractMapper.toDTO(contract);

        assertNotNull(result);
        assertEquals(contractId, result.getId());
        assertEquals(clientId, result.getClientId());
        assertEquals(unitId, result.getUnitId());
        assertEquals(ContractType.VIGILANCIA_PATRIMONIAL, result.getType());
        assertEquals(ContractStatus.ATIVO, result.getStatus());
        assertEquals(new BigDecimal("1000.00"), result.getValue());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
    }

    @Test
    void whenToDTOWithNull_thenReturnNull() {
        ContractDTO result = contractMapper.toDTO(null);
        assertNull(result);
    }

    @Test
    void whenToEntity_thenReturnContract() {
        Contract result = contractMapper.toEntity(contractDTO);

        assertNotNull(result);
        assertEquals(contractId, result.getId());
        assertEquals(ContractType.VIGILANCIA_PATRIMONIAL, result.getType());
        assertEquals(ContractStatus.ATIVO, result.getStatus());
        assertEquals(new BigDecimal("1000.00"), result.getValue());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
    }

    @Test
    void whenToEntityWithNull_thenReturnNull() {
        Contract result = contractMapper.toEntity(null);
        assertNull(result);
    }
} 