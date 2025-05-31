package com.z7design.secured_guard.service;

import java.util.List;
import java.util.UUID;
import com.z7design.secured_guard.model.Unit;

public interface UnitService {
    Unit create(Unit unit);
    Unit update(UUID id, Unit unit);
    void delete(UUID id);
    Unit findById(UUID id);
    Unit findByName(String name);
    Unit findByEmail(String email);
    List<Unit> findByAddressContaining(String address);
    List<Unit> findByParentId(UUID parentId);
    List<Unit> findAll();
}