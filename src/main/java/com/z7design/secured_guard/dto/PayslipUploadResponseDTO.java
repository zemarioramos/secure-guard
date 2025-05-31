package com.z7design.secured_guard.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayslipUploadResponseDTO {
    private int totalPages;
    private int successfulPages;
    private int failedPages;
    private List<PayslipPageResult> results;
    private List<String> errors;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayslipPageResult {
        private int pageNumber;
        private boolean success;
        private String employeeName;
        private String cpf;
        private String month;
        private String error;
    }
} 