package com.z7design.secured_guard.dto;

import com.z7design.secured_guard.model.enums.ScheduleStatus;
import com.z7design.secured_guard.model.enums.Turno;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EscalaDTO {
    private UUID id;
    private LocalDate data;
    private Turno turno;
    private UUID unidadeId;
    private UUID localId;
    private ScheduleStatus status;
    private UUID rotaId;
    private UUID rondaId;
} 