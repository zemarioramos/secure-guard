package com.z7design.secured_guard.dto;

import lombok.Data;

@Data
public class SecurityConfigDTO {
    private String jwtSecret;
    private long jwtExpiration;
    private String allowedOrigins;
    private boolean enableCors;
    private boolean enableCsrf;
    private boolean enableWebSocket;
    private String webSocketEndpoint;
    private String webSocketAllowedOrigins;
} 