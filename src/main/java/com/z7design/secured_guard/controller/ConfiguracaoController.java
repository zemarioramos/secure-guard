package com.z7design.secured_guard.controller;

import com.z7design.secured_guard.dto.EmpresaConfigDTO;
import com.z7design.secured_guard.dto.SecurityConfigDTO;
import com.z7design.secured_guard.dto.SystemConfigDTO;
import com.z7design.secured_guard.service.ConfiguracaoService;
import com.z7design.secured_guard.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/configuracoes")
@RequiredArgsConstructor
public class ConfiguracaoController {
    
    private final ConfiguracaoService configuracaoService;
    private final FileUploadService fileUploadService;
    
    @GetMapping("/empresa")
    public ResponseEntity<EmpresaConfigDTO> getEmpresaConfig() {
        return ResponseEntity.ok(configuracaoService.getEmpresaConfig());
    }
    
    @PutMapping("/empresa")
    public ResponseEntity<EmpresaConfigDTO> updateEmpresaConfig(@Valid @RequestBody EmpresaConfigDTO config) {
        return ResponseEntity.ok(configuracaoService.updateEmpresaConfig(config));
    }
    
    @PostMapping("/empresa/logo")
    public ResponseEntity<Map<String, String>> uploadLogo(@RequestParam("file") MultipartFile file) {
        try {
            String logoPath = fileUploadService.uploadLogo(file);
            configuracaoService.updateLogoPath(logoPath);
            return ResponseEntity.ok(Map.of("logoPath", logoPath, "message", "Logo uploaded successfully"));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to upload logo: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/sistema")
    public ResponseEntity<List<SystemConfigDTO>> getSystemConfigs() {
        return ResponseEntity.ok(configuracaoService.getSystemConfigs());
    }
    
    @PutMapping("/sistema")
    public ResponseEntity<List<SystemConfigDTO>> updateSystemConfigs(@Valid @RequestBody List<SystemConfigDTO> configs) {
        return ResponseEntity.ok(configuracaoService.updateSystemConfigs(configs));
    }
    
    @GetMapping("/seguranca")
    public ResponseEntity<SecurityConfigDTO> getSecurityConfig() {
        return ResponseEntity.ok(configuracaoService.getSecurityConfig());
    }
    
    @PutMapping("/seguranca")
    public ResponseEntity<SecurityConfigDTO> updateSecurityConfig(@Valid @RequestBody SecurityConfigDTO config) {
        return ResponseEntity.ok(configuracaoService.updateSecurityConfig(config));
    }
}