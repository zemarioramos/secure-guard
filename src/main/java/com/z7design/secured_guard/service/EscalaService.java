package com.z7design.secured_guard.service;

import com.z7design.secured_guard.model.Escala;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public interface EscalaService {
    Escala create(Escala escala);
    Escala update(UUID id, Escala escala);
    void delete(UUID id);
    Escala findById(UUID id);
    List<Escala> findAll();
    List<Escala> findByEmployeeId(UUID employeeId);
    List<Escala> findByDate(LocalDate date);
} 