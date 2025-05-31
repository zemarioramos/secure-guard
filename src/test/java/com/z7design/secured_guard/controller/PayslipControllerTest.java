package com.z7design.secured_guard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.z7design.secured_guard.dto.PayslipUploadResponseDTO;
import com.z7design.secured_guard.dto.PayslipUploadResponseDTO.PayslipPageResult;
import com.z7design.secured_guard.service.PayslipService;

@WebMvcTest(PayslipController.class)
class PayslipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayslipService payslipService;

    private MockMultipartFile validPdfFile;
    private MockMultipartFile invalidFile;
    private PayslipUploadResponseDTO successResponse;

    @BeforeEach
    void setUp() {
        validPdfFile = new MockMultipartFile(
            "file",
            "test.pdf",
            "application/pdf",
            "test pdf content".getBytes()
        );

        invalidFile = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "test content".getBytes()
        );

        successResponse = PayslipUploadResponseDTO.builder()
            .totalPages(2)
            .successfulPages(2)
            .failedPages(0)
            .results(Arrays.asList(
                PayslipPageResult.builder()
                    .pageNumber(1)
                    .success(true)
                    .employeeName("João Silva")
                    .cpf("123.456.789-00")
                    .month("Janeiro 2024")
                    .build(),
                PayslipPageResult.builder()
                    .pageNumber(2)
                    .success(true)
                    .employeeName("Maria Santos")
                    .cpf("987.654.321-00")
                    .month("Janeiro 2024")
                    .build()
            ))
            .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenUploadValidPdf_thenReturnSuccess() throws Exception {
        when(payslipService.processPayslipPdf(any())).thenReturn(successResponse);

        mockMvc.perform(multipart("/api/payslips/upload")
                .file(validPdfFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.successfulPages").value(2))
                .andExpect(jsonPath("$.failedPages").value(0))
                .andExpect(jsonPath("$.results[0].employeeName").value("João Silva"))
                .andExpect(jsonPath("$.results[1].employeeName").value("Maria Santos"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenUploadEmptyFile_thenReturnBadRequest() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile(
            "file",
            "empty.pdf",
            "application/pdf",
            new byte[0]
        );

        mockMvc.perform(multipart("/api/payslips/upload")
                .file(emptyFile))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenUploadNonPdfFile_thenReturnBadRequest() throws Exception {
        mockMvc.perform(multipart("/api/payslips/upload")
                .file(invalidFile))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenUploadWithoutPermission_thenReturnForbidden() throws Exception {
        mockMvc.perform(multipart("/api/payslips/upload")
                .file(validPdfFile))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenUploadWithoutAuth_thenReturnUnauthorized() throws Exception {
        mockMvc.perform(multipart("/api/payslips/upload")
                .file(validPdfFile))
                .andExpect(status().isUnauthorized());
    }
} 