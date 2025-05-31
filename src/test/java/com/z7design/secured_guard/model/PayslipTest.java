package com.z7design.secured_guard.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayslipTest {

    private Payslip payslip;
    private UUID id;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        now = LocalDateTime.now();
        
        payslip = Payslip.builder()
                .id(id)
                .employeeName("João Silva")
                .cpf("123.456.789-00")
                .month("Janeiro 2024")
                .pageNumber(1)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    @Test
    void whenCreatePayslip_thenPayslipIsCreated() {
        assertNotNull(payslip);
        assertEquals(id, payslip.getId());
        assertEquals("João Silva", payslip.getEmployeeName());
        assertEquals("123.456.789-00", payslip.getCpf());
        assertEquals("Janeiro 2024", payslip.getMonth());
        assertEquals(1, payslip.getPageNumber());
        assertEquals(now, payslip.getCreatedAt());
        assertEquals(now, payslip.getUpdatedAt());
    }

    @Test
    void whenUpdatePayslip_thenPayslipIsUpdated() {
        payslip.setEmployeeName("Maria Santos");
        payslip.setCpf("987.654.321-00");
        payslip.setMonth("Fevereiro 2024");
        payslip.setPageNumber(2);

        assertEquals("Maria Santos", payslip.getEmployeeName());
        assertEquals("987.654.321-00", payslip.getCpf());
        assertEquals("Fevereiro 2024", payslip.getMonth());
        assertEquals(2, payslip.getPageNumber());
    }

    @Test
    void whenSetTimestamps_thenTimestampsAreSet() {
        LocalDateTime createdAt = now.minusDays(1);
        LocalDateTime updatedAt = now;

        payslip.setCreatedAt(createdAt);
        payslip.setUpdatedAt(updatedAt);

        assertEquals(createdAt, payslip.getCreatedAt());
        assertEquals(updatedAt, payslip.getUpdatedAt());
    }

    @Test
    void whenSetId_thenIdIsSet() {
        UUID newId = UUID.randomUUID();
        payslip.setId(newId);
        assertEquals(newId, payslip.getId());
    }

    @Test
    void whenCreatePayslipWithNoArgsConstructor_thenPayslipIsCreated() {
        Payslip emptyPayslip = new Payslip();
        assertNotNull(emptyPayslip);
        assertNull(emptyPayslip.getId());
        assertNull(emptyPayslip.getEmployeeName());
        assertNull(emptyPayslip.getCpf());
        assertNull(emptyPayslip.getMonth());
        assertEquals(0, emptyPayslip.getPageNumber());
        assertNull(emptyPayslip.getCreatedAt());
        assertNull(emptyPayslip.getUpdatedAt());
    }
} 