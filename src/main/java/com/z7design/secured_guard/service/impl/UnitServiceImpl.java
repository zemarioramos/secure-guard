package com.z7design.secured_guard.service.impl;

import com.z7design.secured_guard.exception.BusinessException;
import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.repository.UnitRepository;
import com.z7design.secured_guard.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    @Transactional
    public Unit create(Unit unit) {
        validateUnit(unit);
        return unitRepository.save(unit);
    }

    @Override
    @Transactional
    public Unit update(UUID id, Unit unit) {
        Unit existingUnit = findById(id);
        validateUnitForUpdate(unit, existingUnit);
        unit.setId(id);
        return unitRepository.save(unit);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Unit unit = findById(id);
        if (!unit.getEmployees().isEmpty()) {
            throw new BusinessException("Cannot delete unit with associated employees");
        }
        unitRepository.delete(unit);
    }

    @Override
    public Unit findById(UUID id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + id));
    }

    @Override
    public Unit findByName(String name) {
        return unitRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with name: " + name));
    }

    @Override
    public Unit findByEmail(String email) {
        return unitRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with email: " + email));
    }

    @Override
    public List<Unit> findByAddressContaining(String address) {
        return unitRepository.findByAddressContaining(address);
    }

    @Override
    public List<Unit> findByParentId(UUID parentId) {
        return unitRepository.findByParentId(parentId);
    }

    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    private void validateUnit(Unit unit) {
        if (unit.getName() == null || unit.getName().trim().isEmpty()) {
            throw new BusinessException("Unit name is required");
        }
        if (unit.getEmail() == null || unit.getEmail().trim().isEmpty()) {
            throw new BusinessException("Unit email is required");
        }
        if (unitRepository.existsByName(unit.getName())) {
            throw new BusinessException("Unit with this name already exists");
        }
        if (unitRepository.existsByEmail(unit.getEmail())) {
            throw new BusinessException("Unit with this email already exists");
        }
    }

    private void validateUnitForUpdate(Unit unit, Unit existingUnit) {
        if (unit.getName() == null || unit.getName().trim().isEmpty()) {
            throw new BusinessException("Unit name is required");
        }
        if (unit.getEmail() == null || unit.getEmail().trim().isEmpty()) {
            throw new BusinessException("Unit email is required");
        }
        if (!unit.getName().equals(existingUnit.getName()) && unitRepository.existsByName(unit.getName())) {
            throw new BusinessException("Unit with this name already exists");
        }
        if (!unit.getEmail().equals(existingUnit.getEmail()) && unitRepository.existsByEmail(unit.getEmail())) {
            throw new BusinessException("Unit with this email already exists");
        }
    }
} 