package com.z7design.secured_guard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.model.ScaleHistory;
import com.z7design.secured_guard.repository.ScaleHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScaleHistoryService {
    
    private final ScaleHistoryRepository scaleHistoryRepository;
    
    @Transactional
    public ScaleHistory create(ScaleHistory scaleHistory) {
        return scaleHistoryRepository.save(scaleHistory);
    }
    
    @Transactional
    public ScaleHistory update(UUID id, ScaleHistory scaleHistory) {
        ScaleHistory existingScaleHistory = findById(id);
        scaleHistory.setId(existingScaleHistory.getId());
        return scaleHistoryRepository.save(scaleHistory);
    }
    
    @Transactional
    public void delete(UUID id) {
        scaleHistoryRepository.deleteById(id);
    }
    
    public ScaleHistory findById(UUID id) {
        return scaleHistoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ScaleHistory not found"));
    }
    
    public List<ScaleHistory> findByEmployeeId(UUID employeeId) {
        return scaleHistoryRepository.findByEmployeeId(employeeId);
    }
    
    public List<ScaleHistory> findByScale(String scale) {
        return scaleHistoryRepository.findByScale(scale);
    }
    
    public List<ScaleHistory> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return scaleHistoryRepository.findByStartDateBetween(startDate, endDate);
    }
    
    public List<ScaleHistory> findByEmployeeIdAndDateRange(UUID employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        return scaleHistoryRepository.findByEmployeeIdAndStartDateBetween(employeeId, startDate, endDate);
    }
    
    public List<ScaleHistory> findCurrentScales() {
        return scaleHistoryRepository.findByEndDateIsNull();
    }
    
    public List<ScaleHistory> findAll() {
        return scaleHistoryRepository.findAll();
    }
} 