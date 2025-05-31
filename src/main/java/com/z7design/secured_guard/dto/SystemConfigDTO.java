package com.z7design.secured_guard.dto;

import lombok.Data;

@Data
public class SystemConfigDTO {
    private String chave;
    private String valor;
    private String descricao;
    private boolean ativo;
} 