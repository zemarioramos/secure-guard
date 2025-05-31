package com.z7design.secured_guard.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.z7design.secured_guard.model.enums.ContractStatus;
import com.z7design.secured_guard.model.enums.ContractType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
    private UUID id;
    private String contractNumber;
    private ContractType type;
    private ContractStatus status;
    private BigDecimal value;
    private UUID clientId;
    private UUID unitId;
    private UUID proposalId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 