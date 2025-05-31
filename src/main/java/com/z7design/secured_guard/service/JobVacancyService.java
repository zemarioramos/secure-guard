package com.z7design.secured_guard.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.model.JobVacancy;
import com.z7design.secured_guard.repository.JobVacancyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobVacancyService {
    
    private final JobVacancyRepository jobVacancyRepository;
    private final NotificationService notificationService;
    
    @Transactional
    public JobVacancy create(JobVacancy jobVacancy) {
        JobVacancy savedVacancy = jobVacancyRepository.save(jobVacancy);
        notificationService.notifyNewJobVacancy(savedVacancy);
        return savedVacancy;
    }
    
    @Transactional
    public JobVacancy update(UUID id, JobVacancy jobVacancy) {
        JobVacancy existingVacancy = findById(id);
        jobVacancy.setId(id);
        return jobVacancyRepository.save(jobVacancy);
    }
    
    @Transactional
    public void delete(UUID id) {
        JobVacancy jobVacancy = findById(id);
        jobVacancyRepository.delete(jobVacancy);
    }
    
    public JobVacancy findById(UUID id) {
        return jobVacancyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
    }
    
    public List<JobVacancy> findByStatus(String status) {
        return jobVacancyRepository.findByStatus(status);
    }
    
    public List<JobVacancy> findByDepartment(String department) {
        return jobVacancyRepository.findByDepartment(department);
    }
    
    public List<JobVacancy> findByPosition(String position) {
        return jobVacancyRepository.findByPosition(position);
    }
    
    public List<JobVacancy> findExpiredVacancies() {
        return jobVacancyRepository.findByDeadlineBefore(LocalDate.now());
    }
    
    public List<JobVacancy> findByStatusAndDepartment(String status, String department) {
        return jobVacancyRepository.findByStatusAndDepartment(status, department);
    }
    
    public List<JobVacancy> findByStatusAndPosition(String status, String position) {
        return jobVacancyRepository.findByStatusAndPosition(status, position);
    }
    
    public List<JobVacancy> findAll() {
        return jobVacancyRepository.findAll();
    }
} 