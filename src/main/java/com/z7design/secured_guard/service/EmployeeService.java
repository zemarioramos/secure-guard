package com.z7design.secured_guard.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Employee;
import com.z7design.secured_guard.model.enums.EmploymentStatus;
import com.z7design.secured_guard.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Transactional
    public Employee create(Employee employee) {
        if (employeeRepository.existsByCpf(employee.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        return employeeRepository.save(employee);
    }
    
    @Transactional
    public Employee update(UUID id, Employee employee) {
        Employee existingEmployee = findById(id);
        
        if (!existingEmployee.getCpf().equals(employee.getCpf()) && 
            employeeRepository.existsByCpf(employee.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        
        if (!existingEmployee.getEmail().equals(employee.getEmail()) && 
            employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        employee.setId(id);
        return employeeRepository.save(employee);
    }
    
    @Transactional
    public void delete(UUID id) {
        Employee employee = findById(id);
        if (employee.getStatus() != EmploymentStatus.TERMINATED) {
            throw new RuntimeException("Apenas funcionários demitidos podem ser excluídos");
        }
        employeeRepository.delete(employee);
    }
    
    public Employee findById(UUID id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
    
    public Optional<Employee> findByCpf(String cpf) {
        return employeeRepository.findByCpf(cpf);
    }
    
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    public List<Employee> findByStatus(EmploymentStatus status) {
        return employeeRepository.findByStatus(status);
    }
    
    public List<Employee> findByUnit(UUID unitId) {
        return employeeRepository.findByUnitId(unitId);
    }
    
    public List<Employee> findByPosition(UUID positionId) {
        return employeeRepository.findByPositionId(positionId);
    }
    
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    @Transactional
    public Employee updateStatus(UUID id, EmploymentStatus status) {
        Employee employee = findById(id);
        employee.setStatus(status);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }
} 