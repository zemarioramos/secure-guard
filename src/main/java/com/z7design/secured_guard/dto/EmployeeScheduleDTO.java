package com.z7design.secured_guard.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class EmployeeScheduleDTO {
    private UUID id;
    private UUID scheduleId;
    private UUID employeeId;
    private UUID positionId;
    private String observations;
} 