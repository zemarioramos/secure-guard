package com.z7design.secured_guard.service;

import com.z7design.secured_guard.dto.EmpresaConfigDTO;
import com.z7design.secured_guard.dto.SecurityConfigDTO;
import com.z7design.secured_guard.dto.SystemConfigDTO;
import com.z7design.secured_guard.entity.Configuracao;
import com.z7design.secured_guard.repository.ConfiguracaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfiguracaoService {
    
    private final ConfiguracaoRepository configuracaoRepository;
    
    public EmpresaConfigDTO getEmpresaConfig() {
        List<Configuracao> configs = configuracaoRepository.findByType("COMPANY");
        Map<String, String> configMap = configs.stream()
            .collect(Collectors.toMap(Configuracao::getKey, Configuracao::getValue));
            
        EmpresaConfigDTO dto = new EmpresaConfigDTO();
        dto.setNome(configMap.getOrDefault("company.name", ""));
        dto.setCnpj(configMap.getOrDefault("company.cnpj", ""));
        dto.setEndereco(configMap.getOrDefault("company.address", ""));
        dto.setTelefone(configMap.getOrDefault("company.phone", ""));
        dto.setEmail(configMap.getOrDefault("company.email", ""));
        dto.setSite(configMap.getOrDefault("company.website", ""));
        dto.setDescricao(configMap.getOrDefault("company.description", ""));
        dto.setLogoPath(configMap.getOrDefault("company.logo", ""));
        
        return dto;
    }
    
    @Transactional
    public EmpresaConfigDTO updateEmpresaConfig(EmpresaConfigDTO config) {
        updateConfig("company.name", config.getNome(), "COMPANY");
        updateConfig("company.cnpj", config.getCnpj(), "COMPANY");
        updateConfig("company.address", config.getEndereco(), "COMPANY");
        updateConfig("company.phone", config.getTelefone(), "COMPANY");
        updateConfig("company.email", config.getEmail(), "COMPANY");
        updateConfig("company.website", config.getSite(), "COMPANY");
        updateConfig("company.description", config.getDescricao(), "COMPANY");
        
        return getEmpresaConfig();
    }
    
    @Transactional
    public void updateLogoPath(String logoPath) {
        updateConfig("company.logo", logoPath, "COMPANY");
    }
    
    public List<SystemConfigDTO> getSystemConfigs() {
        return configuracaoRepository.findByType("SYSTEM").stream()
            .map(this::toSystemConfigDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public List<SystemConfigDTO> updateSystemConfigs(List<SystemConfigDTO> configs) {
        configs.forEach(config -> {
            Configuracao entity = configuracaoRepository.findByKey(config.getChave())
                .orElse(new Configuracao());
            entity.setKey(config.getChave());
            entity.setValue(config.getValor());
            entity.setDescription(config.getDescricao());
            entity.setType("SYSTEM");
            entity.setActive(config.isAtivo());
            configuracaoRepository.save(entity);
        });
        
        return getSystemConfigs();
    }
    
    public SecurityConfigDTO getSecurityConfig() {
        List<Configuracao> configs = configuracaoRepository.findByType("SECURITY");
        Map<String, String> configMap = configs.stream()
            .collect(Collectors.toMap(Configuracao::getKey, Configuracao::getValue));
            
        SecurityConfigDTO dto = new SecurityConfigDTO();
        dto.setJwtSecret(configMap.getOrDefault("security.jwt.secret", ""));
        dto.setJwtExpiration(Long.parseLong(configMap.getOrDefault("security.jwt.expiration", "86400000")));
        dto.setAllowedOrigins(configMap.getOrDefault("security.cors.allowed-origins", "*"));
        dto.setEnableCors(Boolean.parseBoolean(configMap.getOrDefault("security.cors.enabled", "true")));
        dto.setEnableCsrf(Boolean.parseBoolean(configMap.getOrDefault("security.csrf.enabled", "true")));
        dto.setEnableWebSocket(Boolean.parseBoolean(configMap.getOrDefault("websocket.enabled", "true")));
        dto.setWebSocketEndpoint(configMap.getOrDefault("websocket.endpoint", "/ws"));
        dto.setWebSocketAllowedOrigins(configMap.getOrDefault("websocket.allowed-origins", "*"));
        
        return dto;
    }
    
    @Transactional
    public SecurityConfigDTO updateSecurityConfig(SecurityConfigDTO config) {
        updateConfig("security.jwt.secret", config.getJwtSecret(), "SECURITY");
        updateConfig("security.jwt.expiration", String.valueOf(config.getJwtExpiration()), "SECURITY");
        updateConfig("security.cors.allowed-origins", config.getAllowedOrigins(), "SECURITY");
        updateConfig("security.cors.enabled", String.valueOf(config.isEnableCors()), "SECURITY");
        updateConfig("security.csrf.enabled", String.valueOf(config.isEnableCsrf()), "SECURITY");
        updateConfig("websocket.enabled", String.valueOf(config.isEnableWebSocket()), "SYSTEM");
        updateConfig("websocket.endpoint", config.getWebSocketEndpoint(), "SYSTEM");
        updateConfig("websocket.allowed-origins", config.getWebSocketAllowedOrigins(), "SYSTEM");
        
        return getSecurityConfig();
    }
    
    private void updateConfig(String key, String value, String type) {
        Configuracao config = configuracaoRepository.findByKey(key)
            .orElse(new Configuracao());
        config.setKey(key);
        config.setValue(value);
        config.setType(type);
        config.setActive(true);
        configuracaoRepository.save(config);
    }
    
    private SystemConfigDTO toSystemConfigDTO(Configuracao config) {
        SystemConfigDTO dto = new SystemConfigDTO();
        dto.setChave(config.getKey());
        dto.setValor(config.getValue());
        dto.setDescricao(config.getDescription());
        dto.setTipo(config.getType());
        dto.setAtivo(config.isActive());
        return dto;
    }
} 