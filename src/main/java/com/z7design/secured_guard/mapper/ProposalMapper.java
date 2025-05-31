package com.z7design.secured_guard.mapper;

import org.springframework.stereotype.Component;

import com.z7design.secured_guard.dto.ProposalDTO;
import com.z7design.secured_guard.model.Proposal;

@Component
public class ProposalMapper {

    public ProposalDTO toDTO(Proposal proposal) {
        if (proposal == null) {
            return null;
        }

        ProposalDTO dto = new ProposalDTO();
        dto.setId(proposal.getId());
        dto.setProposalNumber(proposal.getProposalNumber());
        dto.setStatus(proposal.getStatus());
        dto.setValue(proposal.getValue());
        dto.setDescription(proposal.getDescription());
        dto.setValidity(proposal.getValidity());
        dto.setContractId(proposal.getContract().getId());
        dto.setCreatedAt(proposal.getCreatedAt());
        dto.setUpdatedAt(proposal.getUpdatedAt());

        return dto;
    }

    public Proposal toEntity(ProposalDTO dto) {
        if (dto == null) {
            return null;
        }

        Proposal proposal = new Proposal();
        proposal.setId(dto.getId());
        proposal.setProposalNumber(dto.getProposalNumber());
        proposal.setStatus(dto.getStatus());
        proposal.setValue(dto.getValue());
        proposal.setDescription(dto.getDescription());
        proposal.setValidity(dto.getValidity());
        proposal.setCreatedAt(dto.getCreatedAt());
        proposal.setUpdatedAt(dto.getUpdatedAt());

        return proposal;
    }
} 