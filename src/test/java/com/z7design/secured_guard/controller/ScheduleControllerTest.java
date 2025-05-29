package com.z7design.secured_guard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.dto.ScheduleDTO;
import com.z7design.secured_guard.dto.EmployeeScheduleDTO;
import com.z7design.secured_guard.model.Schedule;
import com.z7design.secured_guard.model.EmployeeSchedule;
import com.z7design.secured_guard.model.enums.ScheduleStatus;
import com.z7design.secured_guard.model.enums.Shift;
import com.z7design.secured_guard.service.ScheduleService;
import com.z7design.secured_guard.service.LogService;
import com.z7design.secured_guard.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private LogService logService;

    @MockBean
    private JwtService jwtService;

    @Test
    void create_ShouldReturnCreatedSchedule() throws Exception {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        dto.setScheduleDate(LocalDate.now());
        dto.setShift(Shift.MORNING);
        dto.setStatus(ScheduleStatus.PENDING);

        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setScheduleDate(dto.getScheduleDate());
        schedule.setShift(dto.getShift());
        schedule.setStatus(dto.getStatus());

        when(scheduleService.createSchedule(any(ScheduleDTO.class))).thenReturn(schedule);

        // Act & Assert
        mockMvc.perform(post("/api/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.scheduleDate").exists())
                .andExpect(jsonPath("$.shift").value(dto.getShift().name()))
                .andExpect(jsonPath("$.status").value(dto.getStatus().name()));
    }

    @Test
    void createEmployeeSchedule_ShouldReturnCreatedEmployeeSchedule() throws Exception {
        // Arrange
        UUID scheduleId = UUID.randomUUID();
        EmployeeScheduleDTO dto = new EmployeeScheduleDTO();
        dto.setScheduleId(scheduleId);
        dto.setPositionId(UUID.randomUUID());

        EmployeeSchedule employeeSchedule = new EmployeeSchedule();
        employeeSchedule.setId(UUID.randomUUID());

        when(scheduleService.createEmployeeSchedule(any(EmployeeScheduleDTO.class))).thenReturn(employeeSchedule);

        // Act & Assert
        mockMvc.perform(post("/api/schedules/{scheduleId}/employees", scheduleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findByPeriod_ShouldReturnScheduleList() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());

        when(scheduleService.findByPeriod(startDate, endDate)).thenReturn(schedules);

        // Act & Assert
        mockMvc.perform(get("/api/schedules/period")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findByEmployeeId_ShouldReturnScheduleList() throws Exception {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());

        when(scheduleService.findByEmployeeId(employeeId)).thenReturn(schedules);

        // Act & Assert
        mockMvc.perform(get("/api/schedules/employee/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findByDate_ShouldReturnScheduleList() throws Exception {
        // Arrange
        LocalDate date = LocalDate.now();
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());

        when(scheduleService.findByDate(date)).thenReturn(schedules);

        // Act & Assert
        mockMvc.perform(get("/api/schedules/date")
                .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findAll_ShouldReturnAllSchedules() throws Exception {
        // Arrange
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());

        when(scheduleService.findAll()).thenReturn(schedules);

        // Act & Assert
        mockMvc.perform(get("/api/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
} 