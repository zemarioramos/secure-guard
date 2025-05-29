package com.z7design.secured_guard.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Payroll;
import com.z7design.secured_guard.repository.PayrollRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollService {
    
    private final PayrollRepository payrollRepository;
    
    @Transactional
    public Payroll create(Payroll payroll) {
        return payrollRepository.save(payroll);
    }
    
    @Transactional
    public Payroll update(UUID id, Payroll payroll) {
        Payroll existingPayroll = findById(id);
        payroll.setId(id);
        return payrollRepository.save(payroll);
    }
    
    @Transactional
    public void delete(UUID id) {
        Payroll payroll = findById(id);
        payrollRepository.delete(payroll);
    }
    
    public Payroll findById(UUID id) {
        return payrollRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id: " + id));
    }
    
    public List<Payroll> findByEmployeeId(UUID employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }
    
    public List<Payroll> findByUnitId(UUID unitId) {
        return payrollRepository.findByUnitId(unitId);
    }
    
    public List<Payroll> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return payrollRepository.findByDateBetween(startDate, endDate);
    }
    
    public List<Payroll> findByMonth(String month) {
        return payrollRepository.findByMonth(month);
    }
    
    public List<Payroll> findByYear(Integer year) {
        return payrollRepository.findByYear(year);
    }
    
    public List<Payroll> findAll() {
        return payrollRepository.findAll();
    }
} 