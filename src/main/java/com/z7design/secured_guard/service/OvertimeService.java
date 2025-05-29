package com.z7design.secured_guard.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Overtime;
import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.model.enums.OvertimeStatus;
import com.z7design.secured_guard.model.enums.OvertimeType;
import com.z7design.secured_guard.repository.OvertimeRepository;

@Service
public class OvertimeService {
    
    @Autowired
    private OvertimeRepository overtimeRepository;
    
    @Autowired
    private UserService userService;
    
    @Transactional
    public Overtime create(Overtime overtime) {
        validateOvertime(overtime);
        calculateTotalHours(overtime);
        overtime.setStatus(OvertimeStatus.PENDENTE);
        return overtimeRepository.save(overtime);
    }
    
    @Transactional
    public Overtime update(UUID id, Overtime overtime) {
        Overtime existingOvertime = findById(id);
        validateOvertime(overtime);
        calculateTotalHours(overtime);
        
        existingOvertime.setOvertimeDate(overtime.getOvertimeDate());
        existingOvertime.setStartTime(overtime.getStartTime());
        existingOvertime.setEndTime(overtime.getEndTime());
        existingOvertime.setTotalHours(overtime.getTotalHours());
        existingOvertime.setType(overtime.getType());
        existingOvertime.setReason(overtime.getReason());
        
        return overtimeRepository.save(existingOvertime);
    }
    
    @Transactional
    public Overtime approve(UUID id, UUID approvedBy) {
        Overtime overtime = findById(id);
        User approver = userService.findById(approvedBy)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + approvedBy));
        overtime.setStatus(OvertimeStatus.APROVADO);
        overtime.setApprovedBy(approver);
        overtime.setApprovalDate(LocalDateTime.now());
        return overtimeRepository.save(overtime);
    }
    
    @Transactional
    public Overtime reject(UUID id, String justification) {
        Overtime overtime = findById(id);
        overtime.setStatus(OvertimeStatus.REJEITADO);
        overtime.setJustification(justification);
        return overtimeRepository.save(overtime);
    }
    
    @Transactional
    public Overtime compensate(UUID id) {
        Overtime overtime = findById(id);
        overtime.setStatus(OvertimeStatus.COMPENSADO);
        return overtimeRepository.save(overtime);
    }
    
    public Overtime findById(UUID id) {
        return overtimeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Overtime not found with id: " + id));
    }
    
    public List<Overtime> findByEmployeeId(UUID employeeId) {
        return overtimeRepository.findByEmployeeId(employeeId);
    }
    
    public List<Overtime> findByEmployeeIdAndStatus(UUID employeeId, OvertimeStatus status) {
        return overtimeRepository.findByEmployeeIdAndStatus(employeeId, status);
    }
    
    public List<Overtime> findByEmployeeIdAndType(UUID employeeId, OvertimeType type) {
        return overtimeRepository.findByEmployeeIdAndType(employeeId, type);
    }
    
    public List<Overtime> findByEmployeeIdAndDateBetween(UUID employeeId, LocalDate startDate, LocalDate endDate) {
        return overtimeRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
    }
    
    private void validateOvertime(Overtime overtime) {
        if (overtime.getOvertimeDate() == null) {
            throw new IllegalArgumentException("Data do banco de horas é obrigatória");
        }
        
        if (overtime.getStartTime() == null || overtime.getEndTime() == null) {
            throw new IllegalArgumentException("Horário de início e fim são obrigatórios");
        }
        
        if (overtime.getStartTime().isAfter(overtime.getEndTime())) {
            throw new IllegalArgumentException("Horário de início não pode ser posterior ao horário de fim");
        }
        
        if (overtime.getType() == null) {
            throw new IllegalArgumentException("Tipo de banco de horas é obrigatório");
        }
        
        if (overtime.getReason() == null || overtime.getReason().trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo do banco de horas é obrigatório");
        }
    }
    
    private void calculateTotalHours(Overtime overtime) {
        long minutes = ChronoUnit.MINUTES.between(overtime.getStartTime(), overtime.getEndTime());
        overtime.setTotalHours(minutes / 60.0);
    }
} 