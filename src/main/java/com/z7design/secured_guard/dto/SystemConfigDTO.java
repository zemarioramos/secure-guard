package com.z7design.secured_guard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigDTO {
    private String chave;
    private String valor;
    private String descricao;
    private String tipo;
    private boolean ativo;
    
    // Getters e Setters manuais para garantir funcionamento
    public String getChave() { return chave; }
    public void setChave(String chave) { this.chave = chave; }
    
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
} 