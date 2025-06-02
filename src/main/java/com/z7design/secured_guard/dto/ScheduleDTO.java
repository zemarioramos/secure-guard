package com.z7design.secured_guard.dto;

import com.z7design.secured_guard.model.enums.ScheduleStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ScheduleDTO {
    private UUID id;
    private LocalDate scheduleDate;
    private String shift;
    private UUID locationId;
    private UUID routeId;
    private UUID patrolId;
    private ScheduleStatus status;
    private String observations;
} 