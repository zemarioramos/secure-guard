package com.z7design.secured_guard.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.model.enums.ContractType;

@DataJpaTest
class ContractRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContractRepository contractRepository;

    private Contract contract;
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

        contract = Contract.builder()
                .id(contractId)
                .clientId(clientId)
                .unitId(unitId)
                .contractNumber("CONT-001")
                .type(ContractType.PRESTACAO_SERVICOS)
                .status(ContractStatus.ATIVO)
                .startDate(now)
                .endDate(now.plusYears(1))
                .value(new BigDecimal("1000.00"))
                .description("Test Contract")
                .infrastructureDemand("Test infrastructure demand")
                .createdAt(now)
                .updatedAt(now)
                .build();

        entityManager.persist(contract);
        entityManager.flush();
    }

    @Test
    void whenFindById_thenReturnContract() {
        Optional<Contract> found = contractRepository.findById(contractId);

        assertTrue(found.isPresent());
        assertEquals(contractId, found.get().getId());
        assertEquals("CONT-001", found.get().getContractNumber());
    }

    @Test
    void whenFindByStatus_thenReturnContracts() {
        List<Contract> found = contractRepository.findByStatus(ContractStatus.ATIVO);

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
        assertEquals(ContractStatus.ATIVO, found.get(0).getStatus());
    }

    @Test
    void whenFindByUnitId_thenReturnContracts() {
        List<Contract> found = contractRepository.findByUnitId(unitId);

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
        assertEquals(unitId, found.get(0).getUnitId());
    }

    @Test
    void whenFindByClientId_thenReturnContracts() {
        List<Contract> found = contractRepository.findByClientId(clientId);

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
        assertEquals(clientId, found.get(0).getClientId());
    }

    @Test
    void whenFindByStatusAndUnitId_thenReturnContracts() {
        List<Contract> found = contractRepository.findByStatusAndUnitId(ContractStatus.ATIVO, unitId);

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
        assertEquals(ContractStatus.ATIVO, found.get(0).getStatus());
        assertEquals(unitId, found.get(0).getUnitId());
    }

    @Test
    void whenFindAll_thenReturnAllContracts() {
        List<Contract> found = contractRepository.findAll();

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
    }

    @Test
    void whenSave_thenContractIsSaved() {
        Contract newContract = Contract.builder()
                .clientId(clientId)
                .unitId(unitId)
                .contractNumber("CONT-002")
                .type(ContractType.TERCEIRIZACAO)
                .status(ContractStatus.ATIVO)
                .startDate(now)
                .endDate(now.plusYears(1))
                .value(new BigDecimal("2000.00"))
                .description("New Contract")
                .infrastructureDemand("New infrastructure demand")
                .createdAt(now)
                .updatedAt(now)
                .build();

        Contract saved = contractRepository.save(newContract);

        assertNotNull(saved.getId());
        assertEquals("CONT-002", saved.getContractNumber());
        assertEquals(ContractType.TERCEIRIZACAO, saved.getType());
        assertEquals(ContractStatus.ATIVO, saved.getStatus());
        assertEquals(new BigDecimal("2000.00"), saved.getValue());
    }

    @Test
    void whenDelete_thenContractIsDeleted() {
        contractRepository.delete(contract);

        Optional<Contract> found = contractRepository.findById(contractId);
        assertFalse(found.isPresent());
    }
} 