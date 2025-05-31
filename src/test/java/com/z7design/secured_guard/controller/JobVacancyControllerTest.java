package com.z7design.secured_guard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.model.JobVacancy;
import com.z7design.secured_guard.service.JobVacancyService;

@WebMvcTest(JobVacancyController.class)
class JobVacancyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JobVacancyService jobVacancyService;

    private JobVacancy jobVacancy;

    @BeforeEach
    void setUp() {
        jobVacancy = JobVacancy.builder()
                .id(UUID.randomUUID())
                .title("Desenvolvedor Java")
                .description("Vaga para desenvolvedor Java")
                .requirements("Java, Spring Boot, JPA")
                .department("TI")
                .position("Desenvolvedor")
                .quantity(2)
                .salaryRange("R$ 5.000 - R$ 7.000")
                .status("OPEN")
                .deadline(LocalDate.now().plusMonths(1))
                .build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenCreateJobVacancy_thenReturnCreatedJobVacancy() throws Exception {
        when(jobVacancyService.create(any(JobVacancy.class))).thenReturn(jobVacancy);

        mockMvc.perform(post("/api/job-vacancies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jobVacancy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(jobVacancy.getTitle()))
                .andExpect(jsonPath("$.department").value(jobVacancy.getDepartment()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenUpdateJobVacancy_thenReturnUpdatedJobVacancy() throws Exception {
        when(jobVacancyService.update(any(UUID.class), any(JobVacancy.class))).thenReturn(jobVacancy);

        mockMvc.perform(put("/api/job-vacancies/{id}", jobVacancy.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jobVacancy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(jobVacancy.getTitle()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenDeleteJobVacancy_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/job-vacancies/{id}", jobVacancy.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenFindById_thenReturnJobVacancy() throws Exception {
        when(jobVacancyService.findById(jobVacancy.getId())).thenReturn(jobVacancy);

        mockMvc.perform(get("/api/job-vacancies/{id}", jobVacancy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(jobVacancy.getId().toString()))
                .andExpect(jsonPath("$.title").value(jobVacancy.getTitle()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenFindByStatus_thenReturnListOfJobVacancies() throws Exception {
        when(jobVacancyService.findByStatus("OPEN")).thenReturn(List.of(jobVacancy));

        mockMvc.perform(get("/api/job-vacancies/status/OPEN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("OPEN"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenFindByDepartment_thenReturnListOfJobVacancies() throws Exception {
        when(jobVacancyService.findByDepartment("TI")).thenReturn(List.of(jobVacancy));

        mockMvc.perform(get("/api/job-vacancies/department/TI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].department").value("TI"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenFindByPosition_thenReturnListOfJobVacancies() throws Exception {
        when(jobVacancyService.findByPosition("Desenvolvedor")).thenReturn(List.of(jobVacancy));

        mockMvc.perform(get("/api/job-vacancies/position/Desenvolvedor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].position").value("Desenvolvedor"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenFindExpiredVacancies_thenReturnListOfExpiredJobVacancies() throws Exception {
        when(jobVacancyService.findExpiredVacancies()).thenReturn(List.of(jobVacancy));

        mockMvc.perform(get("/api/job-vacancies/expired"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
} 