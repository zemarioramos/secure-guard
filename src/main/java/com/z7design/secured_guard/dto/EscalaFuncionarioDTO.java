package com.z7design.secured_guard.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class EscalaFuncionarioDTO {
    private UUID funcionarioId;
    private UUID positionId;
    private String observacao;
} 