package com.z7design.secured_guard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.model.Employee;
import com.z7design.secured_guard.model.TimeRecord;
import com.z7design.secured_guard.model.enums.TimeRecordStatus;
import com.z7design.secured_guard.service.TimeRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TimeRecordController.class)
class TimeRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimeRecordService timeRecordService;

    private TimeRecord timeRecord;
    private Employee employee;
    private UUID timeRecordId;
    private UUID employeeId;

    @BeforeEach
    void setUp() {
        timeRecordId = UUID.randomUUID();
        employeeId = UUID.randomUUID();

        employee = new Employee();
        employee.setId(employeeId);
        employee.setName("Test Employee");

        timeRecord = TimeRecord.builder()
            .id(timeRecordId)
            .employee(employee)
            .recordDate(LocalDate.now())
            .entryTime(LocalTime.of(9, 0))
            .exitTime(LocalTime.of(18, 0))
            .entryLunchTime(LocalTime.of(12, 0))
            .exitLunchTime(LocalTime.of(13, 0))
            .status(TimeRecordStatus.PENDING)
            .build();
    }

    @Test
    void whenCreateTimeRecord_thenReturnCreatedTimeRecord() throws Exception {
        when(timeRecordService.create(any(TimeRecord.class))).thenReturn(timeRecord);

        mockMvc.perform(post("/api/time-records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(timeRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(timeRecordId.toString()))
                .andExpect(jsonPath("$.status").value(TimeRecordStatus.PENDING.toString()));
    }

    @Test
    void whenApproveTimeRecord_thenReturnApprovedTimeRecord() throws Exception {
        timeRecord.setStatus(TimeRecordStatus.APPROVED);
        when(timeRecordService.approve(timeRecordId)).thenReturn(timeRecord);

        mockMvc.perform(post("/api/time-records/{id}/approve", timeRecordId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(TimeRecordStatus.APPROVED.toString()));
    }

    @Test
    void whenRejectTimeRecord_thenReturnRejectedTimeRecord() throws Exception {
        timeRecord.setStatus(TimeRecordStatus.REJECTED);
        when(timeRecordService.reject(timeRecordId, "Test rejection")).thenReturn(timeRecord);

        mockMvc.perform(post("/api/time-records/{id}/reject", timeRecordId)
                .param("justification", "Test rejection"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(TimeRecordStatus.REJECTED.toString()));
    }

    @Test
    void whenAdjustTimeRecord_thenReturnAdjustedTimeRecord() throws Exception {
        timeRecord.setStatus(TimeRecordStatus.ADJUSTED);
        when(timeRecordService.adjust(
            timeRecordId,
            LocalTime.of(8, 30),
            LocalTime.of(17, 30),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            "Test adjustment"
        )).thenReturn(timeRecord);

        mockMvc.perform(post("/api/time-records/{id}/adjust", timeRecordId)
                .param("entryTime", "08:30")
                .param("exitTime", "17:30")
                .param("entryLunchTime", "12:00")
                .param("exitLunchTime", "13:00")
                .param("justification", "Test adjustment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(TimeRecordStatus.ADJUSTED.toString()));
    }

    @Test
    void whenFindByEmployeeId_thenReturnTimeRecords() throws Exception {
        List<TimeRecord> timeRecords = Arrays.asList(timeRecord);
        when(timeRecordService.findByEmployeeId(employeeId)).thenReturn(timeRecords);

        mockMvc.perform(get("/api/time-records/employee/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(timeRecordId.toString()))
                .andExpect(jsonPath("$[0].status").value(TimeRecordStatus.PENDING.toString()));
    }

    @Test
    void whenFindByEmployeeIdAndPeriod_thenReturnTimeRecords() throws Exception {
        List<TimeRecord> timeRecords = Arrays.asList(timeRecord);
        when(timeRecordService.findByEmployeeIdAndRecordDateBetween(
            employeeId,
            LocalDate.now(),
            LocalDate.now()
        )).thenReturn(timeRecords);

        mockMvc.perform(get("/api/time-records/employee/{employeeId}/period", employeeId)
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(timeRecordId.toString()))
                .andExpect(jsonPath("$[0].status").value(TimeRecordStatus.PENDING.toString()));
    }

    @Test
    void whenFindByEmployeeIdAndStatus_thenReturnTimeRecords() throws Exception {
        List<TimeRecord> timeRecords = Arrays.asList(timeRecord);
        when(timeRecordService.findByEmployeeIdAndStatus(employeeId, TimeRecordStatus.PENDING))
            .thenReturn(timeRecords);

        mockMvc.perform(get("/api/time-records/employee/{employeeId}/status", employeeId)
                .param("status", TimeRecordStatus.PENDING.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(timeRecordId.toString()))
                .andExpect(jsonPath("$[0].status").value(TimeRecordStatus.PENDING.toString()));
    }
} 