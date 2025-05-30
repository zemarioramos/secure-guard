package com.z7design.secured_guard.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.exception.BusinessException;
import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.repository.UnitRepository;

@Service
public class UnitService {
    
    @Autowired
    private UnitRepository unitRepository;
    
    @Transactional
    public Unit create(Unit unit) {
        validateUnit(unit);
        
        if (unitRepository.existsByName(unit.getName())) {
            throw new BusinessException("Unidade já cadastrada");
        }
        if (unit.getEmail() != null && unitRepository.existsByEmail(unit.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }
        return unitRepository.save(unit);
    }
    
    @Transactional
    public Unit update(UUID id, Unit unit) {
        validateUnit(unit);
        Unit existingUnit = findById(id);
        
        if (!existingUnit.getName().equals(unit.getName()) && 
            unitRepository.existsByName(unit.getName())) {
            throw new BusinessException("Unidade já cadastrada");
        }
        
        if (unit.getEmail() != null && !unit.getEmail().equals(existingUnit.getEmail()) && 
            unitRepository.existsByEmail(unit.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }
        
        unit.setId(id);
        return unitRepository.save(unit);
    }
    
    @Transactional
    public void delete(UUID id) {
        Unit unit = findById(id);
        if (!unit.getEmployees().isEmpty()) {
            throw new BusinessException("Não é possível excluir uma unidade que possui funcionários");
        }
        unitRepository.delete(unit);
    }
    
    public Unit findById(UUID id) {
        return unitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + id));
    }
    
    public Unit findByName(String name) {
        return unitRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Unit not found with name: " + name));
    }
    
    public Unit findByEmail(String email) {
        return unitRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Unit not found with email: " + email));
    }
    
    public List<Unit> findByAddressContaining(String address) {
        return unitRepository.findByAddressContaining(address);
    }
    
    public List<Unit> findByParentId(UUID parentId) {
        return unitRepository.findByParentId(parentId);
    }
    
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }
    
    private void validateUnit(Unit unit) {
        if (!StringUtils.hasText(unit.getName())) {
            throw new BusinessException("O nome da unidade é obrigatório");
        }
        if (!StringUtils.hasText(unit.getAddress())) {
            throw new BusinessException("O endereço da unidade é obrigatório");
        }
    }
} 