package com.z7design.secured_guard.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Position;
import com.z7design.secured_guard.repository.PositionRepository;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public Position findById(UUID id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Position not found"));
    }

    public Optional<Position> findByName(String name) {
        return positionRepository.findByName(name);
    }

    public List<Position> findByBaseSalaryGreaterThanEqual(Double baseSalary) {
        return positionRepository.findByBaseSalaryGreaterThanEqual(baseSalary);
    }

    @Transactional
    public Position save(Position position) {
        if (positionRepository.existsByName(position.getName())) {
            throw new RuntimeException("Position already exists");
        }
        return positionRepository.save(position);
    }

    @Transactional
    public void delete(UUID id) {
        positionRepository.deleteById(id);
    }
} 