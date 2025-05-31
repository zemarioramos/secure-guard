package com.z7design.secured_guard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.z7design.secured_guard.dto.PayslipUploadResponseDTO;
import com.z7design.secured_guard.model.Payslip;
import com.z7design.secured_guard.repository.PayslipRepository;

@ExtendWith(MockitoExtension.class)
class PayslipServiceTest {

    @Mock
    private PayslipRepository payslipRepository;

    @InjectMocks
    private PayslipService payslipService;

    private MockMultipartFile pdfFile;
    private PDDocument document;

    @BeforeEach
    void setUp() throws IOException {
        // Criar um PDF de teste com 2 páginas
        document = new PDDocument();
        
        // Página 1
        PDPage page1 = new PDPage();
        document.addPage(page1);
        
        // Página 2
        PDPage page2 = new PDPage();
        document.addPage(page2);
        
        // Salvar o PDF em um array de bytes
        byte[] pdfBytes = document.save(new java.io.ByteArrayOutputStream());
        document.close();
        
        pdfFile = new MockMultipartFile(
            "file",
            "test.pdf",
            "application/pdf",
            pdfBytes
        );
    }

    @Test
    void whenProcessValidPdf_thenReturnSuccess() throws IOException {
        // Configurar o mock do repositório
        when(payslipRepository.save(any(Payslip.class))).thenAnswer(i -> i.getArgument(0));

        // Executar o teste
        PayslipUploadResponseDTO response = payslipService.processPayslipPdf(pdfFile);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(2, response.getTotalPages());
        assertTrue(response.getSuccessfulPages() > 0);
        assertEquals(0, response.getFailedPages());
        assertNotNull(response.getResults());
        assertFalse(response.getResults().isEmpty());

        // Verificar se o repositório foi chamado
        verify(payslipRepository, atLeastOnce()).save(any(Payslip.class));
    }

    @Test
    void whenProcessEmptyPdf_thenReturnEmptyResponse() throws IOException {
        // Criar um PDF vazio
        PDDocument emptyDoc = new PDDocument();
        byte[] emptyPdfBytes = emptyDoc.save(new java.io.ByteArrayOutputStream());
        emptyDoc.close();
        
        MockMultipartFile emptyPdfFile = new MockMultipartFile(
            "file",
            "empty.pdf",
            "application/pdf",
            emptyPdfBytes
        );

        // Executar o teste
        PayslipUploadResponseDTO response = payslipService.processPayslipPdf(emptyPdfFile);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(0, response.getTotalPages());
        assertEquals(0, response.getSuccessfulPages());
        assertEquals(0, response.getFailedPages());
        assertTrue(response.getResults().isEmpty());

        // Verificar que o repositório não foi chamado
        verify(payslipRepository, never()).save(any(Payslip.class));
    }

    @Test
    void whenExtractCPF_thenReturnCorrectCPF() {
        String text = "CPF: 123.456.789-00";
        String cpf = payslipService.extractCPF(text);
        assertEquals("123.456.789-00", cpf);
    }

    @Test
    void whenExtractMonth_thenReturnCorrectMonth() {
        String text = "Holerite - Janeiro 2024";
        String month = payslipService.extractMonth(text);
        assertEquals("Janeiro 2024", month);
    }

    @Test
    void whenExtractEmployeeName_thenReturnCorrectName() {
        String text = "Nome: João Silva\nCPF: 123.456.789-00";
        String name = payslipService.extractEmployeeName(text);
        assertEquals("João Silva", name);
    }

    @Test
    void whenProcessInvalidPdf_thenHandleException() throws IOException {
        // Criar um arquivo PDF inválido
        MockMultipartFile invalidPdfFile = new MockMultipartFile(
            "file",
            "invalid.pdf",
            "application/pdf",
            "invalid content".getBytes()
        );

        // Executar o teste e verificar exceção
        assertThrows(IOException.class, () -> {
            payslipService.processPayslipPdf(invalidPdfFile);
        });

        // Verificar que o repositório não foi chamado
        verify(payslipRepository, never()).save(any(Payslip.class));
    }
} 