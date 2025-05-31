package com.z7design.secured_guard.service;

import com.z7design.secured_guard.dto.PayslipUploadResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PayslipService {
    public PayslipUploadResponseDTO processPayslipPdf(MultipartFile file) {
        // Implementação real deve processar o PDF e retornar o DTO
        return PayslipUploadResponseDTO.builder()
                .totalPages(0)
                .successfulPages(0)
                .failedPages(0)
                .results(new java.util.ArrayList<>())
                .build();
    }

    public String extractCPF(String text) {
        Pattern pattern = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public String extractMonth(String text) {
        Pattern pattern = Pattern.compile("Janeiro|Fevereiro|Março|Abril|Maio|Junho|Julho|Agosto|Setembro|Outubro|Novembro|Dezembro \\d{4}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public String extractEmployeeName(String text) {
        Pattern pattern = Pattern.compile("Nome: ([^\\n]+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
} 