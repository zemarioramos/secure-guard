package com.z7design.secured_guard.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class EmployeeScheduleDTO {
    private UUID id;
    private UUID scheduleId;
    private UUID employeeId;
    private UUID positionId;
    private String observations;
} 