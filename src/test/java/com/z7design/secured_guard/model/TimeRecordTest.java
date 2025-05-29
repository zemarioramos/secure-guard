package com.z7design.secured_guard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.z7design.secured_guard.model.enums.TimeRecordStatus;

class TimeRecordTest {

    private TimeRecord timeRecord;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Test Employee");

        timeRecord = new TimeRecord();
        timeRecord.setId(1L);
        timeRecord.setEmployee(employee);
        timeRecord.setRecordDate(LocalDate.now());
        timeRecord.setEntryTime(LocalTime.of(9, 0));
        timeRecord.setExitTime(LocalTime.of(18, 0));
        timeRecord.setEntryLunchTime(LocalTime.of(12, 0));
        timeRecord.setExitLunchTime(LocalTime.of(13, 0));
        timeRecord.setStatus(TimeRecordStatus.PENDENTE);
    }

    @Test
    void whenCreateTimeRecord_thenTimeRecordIsCreated() {
        assertNotNull(timeRecord);
        assertEquals(employee, timeRecord.getEmployee());
        assertEquals(LocalDate.now(), timeRecord.getRecordDate());
        assertEquals(LocalTime.of(9, 0), timeRecord.getEntryTime());
        assertEquals(LocalTime.of(18, 0), timeRecord.getExitTime());
        assertEquals(LocalTime.of(12, 0), timeRecord.getEntryLunchTime());
        assertEquals(LocalTime.of(13, 0), timeRecord.getExitLunchTime());
        assertEquals(TimeRecordStatus.PENDENTE, timeRecord.getStatus());
    }

    @Test
    void whenUpdateStatus_thenStatusIsUpdated() {
        timeRecord.setStatus(TimeRecordStatus.APROVADO);
        assertEquals(TimeRecordStatus.APROVADO, timeRecord.getStatus());
    }

    @Test
    void whenUpdateJustification_thenJustificationIsUpdated() {
        timeRecord.setJustification("Updated justification");
        assertEquals("Updated justification", timeRecord.getJustification());
    }
} 