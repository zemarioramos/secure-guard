package com.z7design.secured_guard.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JobVacancyTest {

    private JobVacancy jobVacancy;
    private UUID id;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        now = LocalDateTime.now();
        
        jobVacancy = JobVacancy.builder()
                .id(id)
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
    void whenCreateJobVacancy_thenJobVacancyIsCreated() {
        assertNotNull(jobVacancy);
        assertEquals(id, jobVacancy.getId());
        assertEquals("Desenvolvedor Java", jobVacancy.getTitle());
        assertEquals("Vaga para desenvolvedor Java", jobVacancy.getDescription());
        assertEquals("Java, Spring Boot, JPA", jobVacancy.getRequirements());
        assertEquals("TI", jobVacancy.getDepartment());
        assertEquals("Desenvolvedor", jobVacancy.getPosition());
        assertEquals(2, jobVacancy.getQuantity());
        assertEquals("R$ 5.000 - R$ 7.000", jobVacancy.getSalaryRange());
        assertEquals("OPEN", jobVacancy.getStatus());
        assertNotNull(jobVacancy.getDeadline());
    }

    @Test
    void whenUpdateJobVacancy_thenJobVacancyIsUpdated() {
        String newTitle = "Desenvolvedor Java Senior";
        String newDescription = "Vaga para desenvolvedor Java Senior";
        String newRequirements = "Java, Spring Boot, JPA, Microservices";
        String newDepartment = "Desenvolvimento";
        String newPosition = "Desenvolvedor Senior";
        Integer newQuantity = 1;
        String newSalaryRange = "R$ 8.000 - R$ 10.000";
        String newStatus = "CLOSED";
        LocalDate newDeadline = LocalDate.now().plusMonths(2);

        jobVacancy.setTitle(newTitle);
        jobVacancy.setDescription(newDescription);
        jobVacancy.setRequirements(newRequirements);
        jobVacancy.setDepartment(newDepartment);
        jobVacancy.setPosition(newPosition);
        jobVacancy.setQuantity(newQuantity);
        jobVacancy.setSalaryRange(newSalaryRange);
        jobVacancy.setStatus(newStatus);
        jobVacancy.setDeadline(newDeadline);

        assertEquals(newTitle, jobVacancy.getTitle());
        assertEquals(newDescription, jobVacancy.getDescription());
        assertEquals(newRequirements, jobVacancy.getRequirements());
        assertEquals(newDepartment, jobVacancy.getDepartment());
        assertEquals(newPosition, jobVacancy.getPosition());
        assertEquals(newQuantity, jobVacancy.getQuantity());
        assertEquals(newSalaryRange, jobVacancy.getSalaryRange());
        assertEquals(newStatus, jobVacancy.getStatus());
        assertEquals(newDeadline, jobVacancy.getDeadline());
    }

    @Test
    void whenSetTimestamps_thenTimestampsAreSet() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusHours(1);

        jobVacancy.setCreatedAt(createdAt);
        jobVacancy.setUpdatedAt(updatedAt);

        assertEquals(createdAt, jobVacancy.getCreatedAt());
        assertEquals(updatedAt, jobVacancy.getUpdatedAt());
    }

    @Test
    void whenCreateJobVacancyWithNoArgsConstructor_thenJobVacancyIsCreated() {
        JobVacancy emptyJobVacancy = new JobVacancy();

        assertNotNull(emptyJobVacancy);
        assertNull(emptyJobVacancy.getId());
        assertNull(emptyJobVacancy.getTitle());
        assertNull(emptyJobVacancy.getDescription());
        assertNull(emptyJobVacancy.getRequirements());
        assertNull(emptyJobVacancy.getDepartment());
        assertNull(emptyJobVacancy.getPosition());
        assertNull(emptyJobVacancy.getQuantity());
        assertNull(emptyJobVacancy.getSalaryRange());
        assertNull(emptyJobVacancy.getStatus());
        assertNull(emptyJobVacancy.getDeadline());
        assertNull(emptyJobVacancy.getCreatedAt());
        assertNull(emptyJobVacancy.getUpdatedAt());
    }
} 