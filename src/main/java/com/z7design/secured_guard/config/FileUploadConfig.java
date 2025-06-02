package com.z7design.secured_guard.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.file-upload")
public class FileUploadConfig {
    private String uploadDir;
    private String logoDir;
    private long maxFileSize;
    private List<String> allowedImageTypes;
    
    // Getters e Setters manuais para garantir funcionamento
    public String getUploadDir() { return uploadDir; }
    public void setUploadDir(String uploadDir) { this.uploadDir = uploadDir; }
    
    public String getLogoDir() { return logoDir; }
    public void setLogoDir(String logoDir) { this.logoDir = logoDir; }
    
    public long getMaxFileSize() { return maxFileSize; }
    public void setMaxFileSize(long maxFileSize) { this.maxFileSize = maxFileSize; }
    
    public List<String> getAllowedImageTypes() { return allowedImageTypes; }
    public void setAllowedImageTypes(List<String> allowedImageTypes) { this.allowedImageTypes = allowedImageTypes; }
}