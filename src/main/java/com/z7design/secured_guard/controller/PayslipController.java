package com.z7design.secured_guard.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.z7design.secured_guard.dto.PayslipUploadResponseDTO;
import com.z7design.secured_guard.service.PayslipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payslips")
@RequiredArgsConstructor
public class PayslipController {

    private final PayslipService payslipService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<PayslipUploadResponseDTO> uploadPayslipPDF(
            @RequestParam("file") MultipartFile file) throws IOException {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().build();
        }

        PayslipUploadResponseDTO response = payslipService.processPayslipPdf(file);
        return ResponseEntity.ok(response);
    }
} 