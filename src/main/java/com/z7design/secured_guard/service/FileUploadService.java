package com.z7design.secured_guard.service;

import com.z7design.secured_guard.config.FileUploadConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Slf4j  // Adicionar esta anotação
@Service
@RequiredArgsConstructor
public class FileUploadService {
    
    private final FileUploadConfig fileUploadConfig;
    
    public String uploadLogo(MultipartFile file) throws IOException {
        validateFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(fileUploadConfig.getLogoDir());
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        
        log.info("Logo uploaded successfully: {}", fileName);
        return fileName;
    }
    
    public String uploadFile(MultipartFile file) throws IOException {
        validateFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(fileUploadConfig.getUploadDir());
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        
        log.info("File uploaded successfully: {}", fileName);
        return fileName;
    }
    
    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(fileUploadConfig.getUploadDir(), filePath);
            Files.deleteIfExists(path);
            log.info("File deleted: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", filePath, e);
        }
    }
    
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        if (file.getSize() > fileUploadConfig.getMaxFileSize()) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size");
        }
        
        String contentType = file.getContentType();
        if (!fileUploadConfig.getAllowedImageTypes().contains(contentType)) {
            throw new IllegalArgumentException("File type not allowed");
        }
    }
    
    private String generateFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }
    
    private Path createUploadDirectory(String subDir) throws IOException {
        Path uploadPath = Paths.get(fileUploadConfig.getUploadDir(), subDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        return uploadPath;
    }
}