package com.z7design.secured_guard.repository;

import com.z7design.secured_guard.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UnitRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UnitRepository unitRepository;

    private Unit unit;
    private Unit parentUnit;

    @BeforeEach
    void setUp() {
        parentUnit = new Unit();
        parentUnit.setId(UUID.randomUUID());
        parentUnit.setName("Parent Unit");
        parentUnit.setDescription("Parent Description");
        parentUnit.setAddress("Parent Address");
        parentUnit.setPhone("123456789");
        parentUnit.setEmail("parent@example.com");
        parentUnit.setCreatedAt(LocalDateTime.now());
        parentUnit.setUpdatedAt(LocalDateTime.now());

        unit = new Unit();
        unit.setId(UUID.randomUUID());
        unit.setName("Test Unit");
        unit.setDescription("Test Description");
        unit.setAddress("Test Address");
        unit.setPhone("987654321");
        unit.setEmail("test@example.com");
        unit.setParent(parentUnit);
        unit.setCreatedAt(LocalDateTime.now());
        unit.setUpdatedAt(LocalDateTime.now());

        entityManager.persist(parentUnit);
        entityManager.persist(unit);
        entityManager.flush();
    }

    @Test
    void whenFindByName_thenReturnsUnit() {
        Optional<Unit> foundUnit = unitRepository.findByName(unit.getName());

        assertTrue(foundUnit.isPresent());
        assertEquals(unit.getName(), foundUnit.get().getName());
    }

    @Test
    void whenFindByNonExistentName_thenReturnsEmpty() {
        Optional<Unit> foundUnit = unitRepository.findByName("Non Existent");

        assertFalse(foundUnit.isPresent());
    }

    @Test
    void whenFindByEmail_thenReturnsUnit() {
        Optional<Unit> foundUnit = unitRepository.findByEmail(unit.getEmail());

        assertTrue(foundUnit.isPresent());
        assertEquals(unit.getEmail(), foundUnit.get().getEmail());
    }

    @Test
    void whenFindByNonExistentEmail_thenReturnsEmpty() {
        Optional<Unit> foundUnit = unitRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundUnit.isPresent());
    }

    @Test
    void whenFindByAddressContaining_thenReturnsUnits() {
        List<Unit> foundUnits = unitRepository.findByAddressContaining("Test");

        assertFalse(foundUnits.isEmpty());
        assertEquals(1, foundUnits.size());
        assertEquals(unit.getAddress(), foundUnits.get(0).getAddress());
    }

    @Test
    void whenFindByNonExistentAddress_thenReturnsEmpty() {
        List<Unit> foundUnits = unitRepository.findByAddressContaining("Non Existent");

        assertTrue(foundUnits.isEmpty());
    }

    @Test
    void whenFindByParentId_thenReturnsUnits() {
        List<Unit> foundUnits = unitRepository.findByParentId(parentUnit.getId());

        assertFalse(foundUnits.isEmpty());
        assertEquals(1, foundUnits.size());
        assertEquals(parentUnit.getId(), foundUnits.get(0).getParent().getId());
    }

    @Test
    void whenFindByNonExistentParentId_thenReturnsEmpty() {
        List<Unit> foundUnits = unitRepository.findByParentId(UUID.randomUUID());

        assertTrue(foundUnits.isEmpty());
    }

    @Test
    void whenExistsByName_thenReturnsTrue() {
        boolean exists = unitRepository.existsByName(unit.getName());

        assertTrue(exists);
    }

    @Test
    void whenExistsByNonExistentName_thenReturnsFalse() {
        boolean exists = unitRepository.existsByName("Non Existent");

        assertFalse(exists);
    }

    @Test
    void whenExistsByEmail_thenReturnsTrue() {
        boolean exists = unitRepository.existsByEmail(unit.getEmail());

        assertTrue(exists);
    }

    @Test
    void whenExistsByNonExistentEmail_thenReturnsFalse() {
        boolean exists = unitRepository.existsByEmail("nonexistent@example.com");

        assertFalse(exists);
    }
} 