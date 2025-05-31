package com.z7design.secured_guard.mapper;

import org.springframework.stereotype.Component;

import com.z7design.secured_guard.dto.ContractDTO;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.model.enums.ContractType;

@Component
public class ContractMapper {

    public ContractDTO toDTO(Contract contract) {
        if (contract == null) {
            return null;
        }

        ContractDTO dto = new ContractDTO();
        dto.setId(contract.getId());
        dto.setContractNumber(contract.getContractNumber());
        dto.setType(contract.getType());
        dto.setStatus(contract.getStatus());
        dto.setValue(contract.getValue());
        dto.setClientId(contract.getClientId());
        dto.setUnitId(contract.getUnitId());
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());
        dto.setDescription(contract.getDescription());
        if (contract.getProposal() != null) {
            dto.setProposalId(contract.getProposal().getId());
        }
        dto.setCreatedAt(contract.getCreatedAt());
        dto.setUpdatedAt(contract.getUpdatedAt());

        return dto;
    }

    public Contract toEntity(ContractDTO dto) {
        if (dto == null) {
            return null;
        }

        Contract contract = new Contract();
        contract.setId(dto.getId());
        contract.setContractNumber(dto.getContractNumber());
        contract.setType(dto.getType());
        contract.setStatus(dto.getStatus());
        contract.setValue(dto.getValue());
        contract.setClientId(dto.getClientId());
        contract.setUnitId(dto.getUnitId());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());
        contract.setDescription(dto.getDescription());
        contract.setCreatedAt(dto.getCreatedAt());
        contract.setUpdatedAt(dto.getUpdatedAt());

        return contract;
    }
} 