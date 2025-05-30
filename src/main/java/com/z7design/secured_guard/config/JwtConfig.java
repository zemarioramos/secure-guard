package com.z7design.secured_guard.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    @NotBlank(message = "JWT secret não pode estar vazio")
    private String secret;

    @NotNull(message = "JWT expiration não pode ser nulo")
    @Positive(message = "JWT expiration deve ser um valor positivo")
    private Long expiration;

    @NotBlank(message = "JWT header não pode estar vazio")
    private String header;

    @NotBlank(message = "JWT prefix não pode estar vazio")
    private String prefix;

    @PostConstruct
    public void init() {
        log.info("JwtConfig inicializado com as seguintes configurações:");
        log.info("Secret: {}", secret != null ? "Configurado" : "Não configurado");
        log.info("Expiration: {} ms", expiration);
        log.info("Header: {}", header);
        log.info("Prefix: {}", prefix);
    }
} 