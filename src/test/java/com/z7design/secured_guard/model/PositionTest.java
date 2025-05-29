package com.z7design.secured_guard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class PositionTest {

    private Position position;
    private Unit unit;
    private Employee employee;
    private Benefit benefit;

    @BeforeEach
    void setUp() {
        unit = Unit.builder()
                .id(UUID.randomUUID())
                .name("Test Unit")
                .build();

        position = Position.builder()
                .id(UUID.randomUUID())
                .name("Test Position")
                .description("Test Description")
                .unit(unit)
                .build();

        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Test Employee");

        benefit = Benefit.builder()
                .id(UUID.randomUUID())
                .name("Test Benefit")
                .value(1000.0)
                .build();
    }

    @Test
    void whenCreatePosition_thenPositionIsCreated() {
        assertNotNull(position);
        assertEquals("Test Position", position.getName());
        assertEquals("Test Description", position.getDescription());
        assertEquals(unit, position.getUnit());
    }

    @Test
    void whenAddEmployee_thenEmployeeIsAddedToPosition() {
        position.getEmployees().add(employee);
        employee.setPosition(position);

        assertTrue(position.getEmployees().contains(employee));
        assertEquals(position, employee.getPosition());
    }

    @Test
    void whenAddBenefit_thenBenefitIsAddedToPosition() {
        position.getBenefits().add(benefit);
        benefit.setPosition(position);

        assertTrue(position.getBenefits().contains(benefit));
        assertEquals(position, benefit.getPosition());
    }
} 