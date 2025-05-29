package com.z7design.secured_guard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Dependent;
import com.z7design.secured_guard.repository.DependentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DependentService {
    
    private final DependentRepository dependentRepository;
    
    @Transactional
    public Dependent create(Dependent dependent) {
        validateDependent(dependent);
        return dependentRepository.save(dependent);
    }
    
    @Transactional
    public Dependent update(Long id, Dependent dependent) {
        Dependent existingDependent = findById(id);
        validateDependent(dependent);
        
        existingDependent.setName(dependent.getName());
        existingDependent.setRelationship(dependent.getRelationship());
        existingDependent.setBirthDate(dependent.getBirthDate());
        existingDependent.setCpf(dependent.getCpf());
        existingDependent.setRg(dependent.getRg());
        
        return dependentRepository.save(existingDependent);
    }
    
    @Transactional
    public void delete(Long id) {
        Dependent dependent = findById(id);
        dependentRepository.delete(dependent);
    }
    
    public Dependent findById(Long id) {
        return dependentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Dependente não encontrado"));
    }
    
    public List<Dependent> findByEmployeeId(Long employeeId) {
        return dependentRepository.findByEmployeeId(employeeId);
    }
    
    public List<Dependent> findByCpf(String cpf) {
        return dependentRepository.findByCpf(cpf);
    }
    
    public List<Dependent> findByRelationship(String relationship) {
        return dependentRepository.findByRelationship(relationship);
    }
    
    private void validateDependent(Dependent dependent) {
        if (dependent.getName() == null || dependent.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do dependente é obrigatório");
        }
        
        if (dependent.getRelationship() == null || dependent.getRelationship().trim().isEmpty()) {
            throw new IllegalArgumentException("Relação com o dependente é obrigatória");
        }
        
        if (dependent.getBirthDate() == null) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória");
        }
    }
} 