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
import com.z7design.secured_guard.model.Client;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.model.enums.ContractType;
import com.z7design.secured_guard.repository.ContractRepository;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ContractService contractService;

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
                .contractNumber("CONT-001")
                .type(ContractType.PRESTACAO_SERVICOS)
                .status(ContractStatus.ATIVO)
                .client(client)
                .unit(unit)
                .startDate(now)
                .endDate(now.plusYears(1))
                .value(new BigDecimal("1000.0"))
                .description("Test Contract")
                .build();
    }

    @Test
    void whenCreateContract_thenContractIsCreated() {
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);

        Contract createdContract = contractService.create(contract);

        assertNotNull(createdContract);
        assertEquals(contractId, createdContract.getId());
        assertEquals("CONT-001", createdContract.getContractNumber());
        verify(contractRepository).save(any(Contract.class));
        verify(notificationService).notifyContractCreated(contract);
    }

    @Test
    void whenUpdateContract_thenContractIsUpdated() {
        when(contractRepository.findById(any(UUID.class))).thenReturn(Optional.of(contract));
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);

        contract.setContractNumber("CONT-002");
        contract.setType(ContractType.TERCEIRIZACAO);
        contract.setStatus(ContractStatus.SUSPENSO);
        contract.setValue(new BigDecimal("2000.0"));
        contract.setDescription("Updated Contract");

        Contract updatedContract = contractService.update(contractId, contract);

        assertNotNull(updatedContract);
        assertEquals("CONT-002", updatedContract.getContractNumber());
        assertEquals(ContractType.TERCEIRIZACAO, updatedContract.getType());
        assertEquals(ContractStatus.SUSPENSO, updatedContract.getStatus());
        assertEquals(new BigDecimal("2000.0"), updatedContract.getValue());
        assertEquals("Updated Contract", updatedContract.getDescription());
        verify(contractRepository).findById(contractId);
        verify(contractRepository).save(any(Contract.class));
    }

    @Test
    void whenUpdateNonExistentContract_thenThrowException() {
        when(contractRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contractService.update(contractId, contract);
        });

        verify(contractRepository).findById(contractId);
        verify(contractRepository, never()).save(any(Contract.class));
    }

    @Test
    void whenDeleteContract_thenContractIsDeleted() {
        when(contractRepository.findById(any(UUID.class))).thenReturn(Optional.of(contract));
        doNothing().when(contractRepository).delete(any(Contract.class));

        contractService.delete(contractId);

        verify(contractRepository).findById(contractId);
        verify(contractRepository).delete(contract);
    }

    @Test
    void whenDeleteNonExistentContract_thenThrowException() {
        when(contractRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contractService.delete(contractId);
        });

        verify(contractRepository).findById(contractId);
        verify(contractRepository, never()).delete(any(Contract.class));
    }

    @Test
    void whenFindById_thenReturnContract() {
        when(contractRepository.findById(any(UUID.class))).thenReturn(Optional.of(contract));

        Contract foundContract = contractService.findById(contractId);

        assertNotNull(foundContract);
        assertEquals(contractId, foundContract.getId());
        verify(contractRepository).findById(contractId);
    }

    @Test
    void whenFindNonExistentContractById_thenThrowException() {
        when(contractRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contractService.findById(contractId);
        });

        verify(contractRepository).findById(contractId);
    }

    @Test
    void whenFindByStatus_thenReturnContracts() {
        List<Contract> contracts = Arrays.asList(contract);
        when(contractRepository.findByStatus(any(ContractStatus.class))).thenReturn(contracts);

        List<Contract> foundContracts = contractService.findByStatus(ContractStatus.ATIVO);

        assertNotNull(foundContracts);
        assertEquals(1, foundContracts.size());
        assertEquals(ContractStatus.ATIVO, foundContracts.get(0).getStatus());
        verify(contractRepository).findByStatus(ContractStatus.ATIVO);
    }

    @Test
    void whenFindByUnit_thenReturnContracts() {
        List<Contract> contracts = Arrays.asList(contract);
        when(contractRepository.findByUnitId(any(UUID.class))).thenReturn(contracts);

        List<Contract> foundContracts = contractService.findByUnit(unitId);

        assertNotNull(foundContracts);
        assertEquals(1, foundContracts.size());
        assertEquals(unitId, foundContracts.get(0).getUnitId());
        verify(contractRepository).findByUnitId(unitId);
    }

    @Test
    void whenFindByClient_thenReturnContracts() {
        List<Contract> contracts = Arrays.asList(contract);
        when(contractRepository.findByClientId(any(UUID.class))).thenReturn(contracts);

        List<Contract> foundContracts = contractService.findByClient(clientId);

        assertNotNull(foundContracts);
        assertEquals(1, foundContracts.size());
        assertEquals(clientId, foundContracts.get(0).getClientId());
        verify(contractRepository).findByClientId(clientId);
    }

    @Test
    void whenFindByStatusAndUnit_thenReturnContracts() {
        List<Contract> contracts = Arrays.asList(contract);
        when(contractRepository.findByStatusAndUnitId(any(ContractStatus.class), any(UUID.class))).thenReturn(contracts);

        List<Contract> foundContracts = contractService.findByStatusAndUnit(ContractStatus.ATIVO, unitId);

        assertNotNull(foundContracts);
        assertEquals(1, foundContracts.size());
        assertEquals(ContractStatus.ATIVO, foundContracts.get(0).getStatus());
        assertEquals(unitId, foundContracts.get(0).getUnitId());
        verify(contractRepository).findByStatusAndUnitId(ContractStatus.ATIVO, unitId);
    }

    @Test
    void whenFindAll_thenReturnAllContracts() {
        List<Contract> contracts = Arrays.asList(contract);
        when(contractRepository.findAll()).thenReturn(contracts);

        List<Contract> foundContracts = contractService.findAll();

        assertNotNull(foundContracts);
        assertEquals(1, foundContracts.size());
        verify(contractRepository).findAll();
    }
} 