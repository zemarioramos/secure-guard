package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.dto.ContractDTO;
import com.z7design.secured_guard.mapper.ContractMapper;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.service.ContractService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<ContractDTO> create(@RequestBody ContractDTO contractDTO) {
        Contract contract = contractMapper.toEntity(contractDTO);
        Contract createdContract = contractService.create(contract);
        return ResponseEntity.ok(contractMapper.toDTO(createdContract));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<ContractDTO> update(@PathVariable UUID id, @RequestBody ContractDTO contractDTO) {
        Contract contract = contractMapper.toEntity(contractDTO);
        Contract updatedContract = contractService.update(id, contract);
        return ResponseEntity.ok(contractMapper.toDTO(updatedContract));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        contractService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<ContractDTO> findById(@PathVariable UUID id) {
        Contract contract = contractService.findById(id);
        return ResponseEntity.ok(contractMapper.toDTO(contract));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ContractDTO>> findByStatus(@PathVariable String status) {
        try {
            ContractStatus contractStatus = ContractStatus.valueOf(status.toUpperCase());
            List<Contract> contracts = contractService.findByStatus(contractStatus);
            List<ContractDTO> contractDTOs = contracts.stream()
                    .map(contractMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(contractDTOs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/unit/{unitId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ContractDTO>> findByUnit(@PathVariable UUID unitId) {
        List<Contract> contracts = contractService.findByUnit(unitId);
        List<ContractDTO> contractDTOs = contracts.stream()
                .map(contractMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contractDTOs);
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ContractDTO>> findByClient(@PathVariable UUID clientId) {
        List<Contract> contracts = contractService.findByClient(clientId);
        List<ContractDTO> contractDTOs = contracts.stream()
                .map(contractMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contractDTOs);
    }

    @GetMapping("/status/{status}/unit/{unitId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ContractDTO>> findByStatusAndUnit(
            @PathVariable String status,
            @PathVariable UUID unitId) {
        try {
            ContractStatus contractStatus = ContractStatus.valueOf(status.toUpperCase());
            List<Contract> contracts = contractService.findByStatusAndUnit(contractStatus, unitId);
            List<ContractDTO> contractDTOs = contracts.stream()
                    .map(contractMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(contractDTOs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ContractDTO>> findAll() {
        List<Contract> contracts = contractService.findAll();
        List<ContractDTO> contractDTOs = contracts.stream()
                .map(contractMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contractDTOs);
    }
} 