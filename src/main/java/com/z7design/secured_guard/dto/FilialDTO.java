package com.z7design.secured_guard.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilialDTO {
    private UUID id;
    private String nome;
    private String endereco;
    private String telefone;
    private String responsavel;
    private Integer funcionarios;
    private Boolean ativa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}