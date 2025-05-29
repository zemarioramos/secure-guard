package com.z7design.secured_guard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

class OvertimeTest {

    private Overtime overtime;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Test Employee");

        overtime = new Overtime();
        overtime.setId(UUID.randomUUID());
        overtime.setEmployee(employee);
        overtime.setDate(LocalDate.now());
        overtime.setHours(2.5);
        overtime.setReason("Extra work");
    }

    @Test
    void whenCreateOvertime_thenOvertimeIsCreated() {
        assertNotNull(overtime);
        assertEquals(employee, overtime.getEmployee());
        assertEquals(LocalDate.now(), overtime.getDate());
        assertEquals(2.5, overtime.getHours());
        assertEquals("Extra work", overtime.getReason());
    }

    @Test
    void whenUpdateHours_thenHoursIsUpdated() {
        overtime.setHours(3.0);
        assertEquals(3.0, overtime.getHours());
    }

    @Test
    void whenUpdateReason_thenReasonIsUpdated() {
        overtime.setReason("Updated reason");
        assertEquals("Updated reason", overtime.getReason());
    }
} 