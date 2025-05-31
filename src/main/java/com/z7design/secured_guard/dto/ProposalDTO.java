package com.z7design.secured_guard.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.z7design.secured_guard.model.enums.ProposalStatus;

import lombok.Data;

@Data
public class ProposalDTO {
    private UUID id;
    private String proposalNumber;
    private ProposalStatus status;
    private BigDecimal value;
    private String description;
    private LocalDateTime validity;
    private UUID contractId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 