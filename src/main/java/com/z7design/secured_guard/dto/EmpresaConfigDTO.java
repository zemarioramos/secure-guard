package com.z7design.secured_guard.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaConfigDTO {
    
    private UUID id;
    
    @NotBlank(message = "Nome da empresa é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
    private String cnpj;
    
    @Pattern(regexp = "\\d{9}", message = "Inscrição estadual deve conter 9 dígitos")
    private String inscricaoEstadual;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 200, message = "Endereço não pode exceder 200 caracteres")
    private String endereco;
    
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    private String telefone;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}(/.*)?$", message = "Website deve ter um formato válido")
    private String website;
    
    private String logoPath;
    
    private String site;
    
    private String descricao;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}