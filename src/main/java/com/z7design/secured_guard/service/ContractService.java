package com.z7design.secured_guard.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.repository.ContractRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractService {
    
    private final ContractRepository contractRepository;
    private final NotificationService notificationService;
    
    @Transactional
    public Contract create(Contract contract) {
        Contract savedContract = contractRepository.save(contract);
        notificationService.notifyContractCreated(savedContract);
        return savedContract;
    }
    
    @Transactional
    public Contract update(UUID id, Contract contract) {
        Contract existingContract = findById(id);
        contract.setId(id);
        return contractRepository.save(contract);
    }
    
    @Transactional
    public void delete(UUID id) {
        Contract contract = findById(id);
        contractRepository.delete(contract);
    }
    
    public Contract findById(UUID id) {
        return contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
    }
    
    public List<Contract> findByStatus(ContractStatus status) {
        return contractRepository.findByStatus(status);
    }
    
    public List<Contract> findByUnit(UUID unitId) {
        return contractRepository.findByUnitId(unitId);
    }
    
    public List<Contract> findByClient(UUID clientId) {
        return contractRepository.findByClientId(clientId);
    }
    
    public List<Contract> findByStatusAndUnit(ContractStatus status, UUID unitId) {
        return contractRepository.findByStatusAndUnitId(status, unitId);
    }
    
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }
} 