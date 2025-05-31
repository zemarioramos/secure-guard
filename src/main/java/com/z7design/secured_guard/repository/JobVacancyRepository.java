package com.z7design.secured_guard.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.JobVacancy;

@Repository
public interface JobVacancyRepository extends JpaRepository<JobVacancy, UUID> {
    
    List<JobVacancy> findByStatus(String status);
    
    List<JobVacancy> findByDepartment(String department);
    
    List<JobVacancy> findByPosition(String position);
    
    List<JobVacancy> findByDeadlineBefore(LocalDate date);
    
    List<JobVacancy> findByStatusAndDepartment(String status, String department);
    
    List<JobVacancy> findByStatusAndPosition(String status, String position);
} 