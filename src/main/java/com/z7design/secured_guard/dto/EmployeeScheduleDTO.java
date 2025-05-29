package com.z7design.secured_guard.dto;

import com.z7design.secured_guard.model.enums.ScheduleStatus;
import com.z7design.secured_guard.model.enums.Shift;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EmployeeScheduleDTO {
    private UUID id;
    private UUID scheduleId;
    private UUID employeeId;
    private UUID positionId;
    private LocalDate scheduleDate;
    private Shift shift;
    private ScheduleStatus status;
    private String observations;
} 