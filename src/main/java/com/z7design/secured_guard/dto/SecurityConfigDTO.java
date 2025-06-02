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
public class SecurityConfigDTO {
    private String jwtSecret;
    private long jwtExpiration;
    private String allowedOrigins;
    private boolean enableCors;
    private boolean enableCsrf;
    private boolean enableWebSocket;
    private String webSocketEndpoint;
    private String webSocketAllowedOrigins;
    
    // Getters e Setters manuais para garantir funcionamento
    public String getJwtSecret() { return jwtSecret; }
    public void setJwtSecret(String jwtSecret) { this.jwtSecret = jwtSecret; }
    
    public long getJwtExpiration() { return jwtExpiration; }
    public void setJwtExpiration(long jwtExpiration) { this.jwtExpiration = jwtExpiration; }
    
    public String getAllowedOrigins() { return allowedOrigins; }
    public void setAllowedOrigins(String allowedOrigins) { this.allowedOrigins = allowedOrigins; }
    
    public boolean isEnableCors() { return enableCors; }
    public void setEnableCors(boolean enableCors) { this.enableCors = enableCors; }
    
    public boolean isEnableCsrf() { return enableCsrf; }
    public void setEnableCsrf(boolean enableCsrf) { this.enableCsrf = enableCsrf; }
    
    public boolean isEnableWebSocket() { return enableWebSocket; }
    public void setEnableWebSocket(boolean enableWebSocket) { this.enableWebSocket = enableWebSocket; }
    
    public String getWebSocketEndpoint() { return webSocketEndpoint; }
    public void setWebSocketEndpoint(String webSocketEndpoint) { this.webSocketEndpoint = webSocketEndpoint; }
    
    public String getWebSocketAllowedOrigins() { return webSocketAllowedOrigins; }
    public void setWebSocketAllowedOrigins(String webSocketAllowedOrigins) { this.webSocketAllowedOrigins = webSocketAllowedOrigins; }
} 