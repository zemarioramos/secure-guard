package com.z7design.secured_guard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.z7design.secured_guard.model.JobVacancy;
import com.z7design.secured_guard.repository.JobVacancyRepository;

@ExtendWith(MockitoExtension.class)
class JobVacancyServiceTest {

    @Mock
    private JobVacancyRepository jobVacancyRepository;
    
    @Mock
    private NotificationService notificationService;
    
    @InjectMocks
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
    void whenCreateJobVacancy_thenReturnSavedJobVacancy() {
        when(jobVacancyRepository.save(any(JobVacancy.class))).thenReturn(jobVacancy);
        
        JobVacancy savedVacancy = jobVacancyService.create(jobVacancy);
        
        assertNotNull(savedVacancy);
        assertEquals(jobVacancy.getTitle(), savedVacancy.getTitle());
        verify(jobVacancyRepository).save(any(JobVacancy.class));
        verify(notificationService).notifyNewJobVacancy(any(JobVacancy.class));
    }
    
    @Test
    void whenUpdateJobVacancy_thenReturnUpdatedJobVacancy() {
        when(jobVacancyRepository.findById(jobVacancy.getId())).thenReturn(Optional.of(jobVacancy));
        when(jobVacancyRepository.save(any(JobVacancy.class))).thenReturn(jobVacancy);
        
        JobVacancy updatedVacancy = jobVacancyService.update(jobVacancy.getId(), jobVacancy);
        
        assertNotNull(updatedVacancy);
        assertEquals(jobVacancy.getTitle(), updatedVacancy.getTitle());
        verify(jobVacancyRepository).save(any(JobVacancy.class));
    }
    
    @Test
    void whenDeleteJobVacancy_thenDeleteIsCalled() {
        when(jobVacancyRepository.findById(jobVacancy.getId())).thenReturn(Optional.of(jobVacancy));
        
        jobVacancyService.delete(jobVacancy.getId());
        
        verify(jobVacancyRepository).delete(jobVacancy);
    }
    
    @Test
    void whenFindById_thenReturnJobVacancy() {
        when(jobVacancyRepository.findById(jobVacancy.getId())).thenReturn(Optional.of(jobVacancy));
        
        JobVacancy foundVacancy = jobVacancyService.findById(jobVacancy.getId());
        
        assertNotNull(foundVacancy);
        assertEquals(jobVacancy.getId(), foundVacancy.getId());
    }
    
    @Test
    void whenFindByStatus_thenReturnListOfJobVacancies() {
        List<JobVacancy> vacancies = List.of(jobVacancy);
        when(jobVacancyRepository.findByStatus("OPEN")).thenReturn(vacancies);
        
        List<JobVacancy> foundVacancies = jobVacancyService.findByStatus("OPEN");
        
        assertNotNull(foundVacancies);
        assertEquals(1, foundVacancies.size());
        assertEquals(jobVacancy.getStatus(), foundVacancies.get(0).getStatus());
    }
    
    @Test
    void whenFindByDepartment_thenReturnListOfJobVacancies() {
        List<JobVacancy> vacancies = List.of(jobVacancy);
        when(jobVacancyRepository.findByDepartment("TI")).thenReturn(vacancies);
        
        List<JobVacancy> foundVacancies = jobVacancyService.findByDepartment("TI");
        
        assertNotNull(foundVacancies);
        assertEquals(1, foundVacancies.size());
        assertEquals(jobVacancy.getDepartment(), foundVacancies.get(0).getDepartment());
    }
    
    @Test
    void whenFindByPosition_thenReturnListOfJobVacancies() {
        List<JobVacancy> vacancies = List.of(jobVacancy);
        when(jobVacancyRepository.findByPosition("Desenvolvedor")).thenReturn(vacancies);
        
        List<JobVacancy> foundVacancies = jobVacancyService.findByPosition("Desenvolvedor");
        
        assertNotNull(foundVacancies);
        assertEquals(1, foundVacancies.size());
        assertEquals(jobVacancy.getPosition(), foundVacancies.get(0).getPosition());
    }
    
    @Test
    void whenFindExpiredVacancies_thenReturnListOfExpiredJobVacancies() {
        List<JobVacancy> vacancies = List.of(jobVacancy);
        when(jobVacancyRepository.findByDeadlineBefore(any(LocalDate.class))).thenReturn(vacancies);
        
        List<JobVacancy> foundVacancies = jobVacancyService.findExpiredVacancies();
        
        assertNotNull(foundVacancies);
        assertEquals(1, foundVacancies.size());
    }
} 