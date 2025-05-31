package com.z7design.secured_guard.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadConfig {
    private String uploadDir;
    private String logoDir;
    private long maxFileSize;
    private List<String> allowedImageTypes;
}