package com.z7design.secured_guard.dto;

import com.z7design.secured_guard.model.enums.ScheduleStatus;
import com.z7design.secured_guard.model.enums.Shift;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ScheduleDTO {
    private UUID id;
    private LocalDate scheduleDate;
    private Shift shift;
    private UUID locationId;
    private ScheduleStatus status;
    private UUID routeId;
    private UUID patrolId;
    private String observations;
} 