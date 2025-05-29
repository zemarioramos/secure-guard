package com.z7design.secured_guard.controller;

import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.service.UnitService;
import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitControllerTest {

    @Mock
    private UnitService unitService;

    @InjectMocks
    private UnitController unitController;

    private Unit unit;
    private Unit parentUnit;

    @BeforeEach
    void setUp() {
        unit = Unit.builder()
                .id(UUID.randomUUID())
                .name("Test Unit")
                .description("Test Description")
                .address("Test Address")
                .phone("123456789")
                .email("test@example.com")
                .build();

        parentUnit = Unit.builder()
                .id(UUID.randomUUID())
                .name("Parent Unit")
                .build();
    }

    @Test
    void whenCreateUnit_thenReturnsCreatedUnit() {
        when(unitService.create(any(Unit.class))).thenReturn(unit);

        ResponseEntity<Unit> response = unitController.create(unit);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(unit.getName(), response.getBody().getName());
        verify(unitService).create(unit);
    }

    @Test
    void whenCreateUnitWithInvalidData_thenReturnsBadRequest() {
        Unit invalidUnit = Unit.builder().build();
        when(unitService.create(any(Unit.class))).thenThrow(new BusinessException("Invalid data"));

        assertThrows(BusinessException.class, () -> unitController.create(invalidUnit));
        verify(unitService).create(invalidUnit);
    }

    @Test
    void whenUpdateUnit_thenReturnsUpdatedUnit() {
        when(unitService.update(any(UUID.class), any(Unit.class))).thenReturn(unit);

        ResponseEntity<Unit> response = unitController.update(unit.getId(), unit);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(unit.getName(), response.getBody().getName());
        verify(unitService).update(unit.getId(), unit);
    }

    @Test
    void whenUpdateNonExistentUnit_thenReturnsNotFound() {
        when(unitService.update(any(UUID.class), any(Unit.class)))
                .thenThrow(new ResourceNotFoundException("Unit not found"));

        assertThrows(ResourceNotFoundException.class, () -> unitController.update(UUID.randomUUID(), unit));
        verify(unitService).update(any(UUID.class), any(Unit.class));
    }

    @Test
    void whenUpdateUnitWithInvalidData_thenReturnsBadRequest() {
        Unit invalidUnit = Unit.builder().build();
        when(unitService.update(any(UUID.class), any(Unit.class)))
                .thenThrow(new BusinessException("Invalid data"));

        assertThrows(BusinessException.class, () -> unitController.update(unit.getId(), invalidUnit));
        verify(unitService).update(any(UUID.class), any(Unit.class));
    }

    @Test
    void whenDeleteUnit_thenReturnsNoContent() {
        doNothing().when(unitService).delete(any(UUID.class));

        ResponseEntity<Void> response = unitController.delete(unit.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(unitService).delete(unit.getId());
    }

    @Test
    void whenDeleteNonExistentUnit_thenReturnsNotFound() {
        doThrow(new ResourceNotFoundException("Unit not found")).when(unitService).delete(any(UUID.class));

        assertThrows(ResourceNotFoundException.class, () -> unitController.delete(UUID.randomUUID()));
        verify(unitService).delete(any(UUID.class));
    }

    @Test
    void whenDeleteUnitWithEmployees_thenReturnsBadRequest() {
        doThrow(new BusinessException("Cannot delete unit with employees"))
                .when(unitService).delete(any(UUID.class));

        assertThrows(BusinessException.class, () -> unitController.delete(unit.getId()));
        verify(unitService).delete(unit.getId());
    }

    @Test
    void whenFindById_thenReturnsUnit() {
        when(unitService.findById(any(UUID.class))).thenReturn(unit);

        ResponseEntity<Unit> response = unitController.findById(unit.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(unit.getId(), response.getBody().getId());
        verify(unitService).findById(unit.getId());
    }

    @Test
    void whenFindByNonExistentId_thenReturnsNotFound() {
        when(unitService.findById(any(UUID.class)))
                .thenThrow(new ResourceNotFoundException("Unit not found"));

        assertThrows(ResourceNotFoundException.class, () -> unitController.findById(UUID.randomUUID()));
        verify(unitService).findById(any(UUID.class));
    }

    @Test
    void whenFindByName_thenReturnsUnit() {
        when(unitService.findByName(anyString())).thenReturn(unit);

        ResponseEntity<Unit> response = unitController.findByName(unit.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(unit.getName(), response.getBody().getName());
        verify(unitService).findByName(unit.getName());
    }

    @Test
    void whenFindByNonExistentName_thenReturnsNotFound() {
        when(unitService.findByName(anyString()))
                .thenThrow(new ResourceNotFoundException("Unit not found"));

        assertThrows(ResourceNotFoundException.class, () -> unitController.findByName("Non Existent"));
        verify(unitService).findByName(anyString());
    }

    @Test
    void whenFindByEmail_thenReturnsUnit() {
        when(unitService.findByEmail(anyString())).thenReturn(unit);

        ResponseEntity<Unit> response = unitController.findByEmail(unit.getEmail());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(unit.getEmail(), response.getBody().getEmail());
        verify(unitService).findByEmail(unit.getEmail());
    }

    @Test
    void whenFindByNonExistentEmail_thenReturnsNotFound() {
        when(unitService.findByEmail(anyString()))
                .thenThrow(new ResourceNotFoundException("Unit not found"));

        assertThrows(ResourceNotFoundException.class, () -> unitController.findByEmail("nonexistent@example.com"));
        verify(unitService).findByEmail(anyString());
    }

    @Test
    void whenFindByAddressContaining_thenReturnsUnits() {
        List<Unit> units = Arrays.asList(unit);
        when(unitService.findByAddressContaining(anyString())).thenReturn(units);

        ResponseEntity<List<Unit>> response = unitController.findByAddressContaining(unit.getAddress());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(unitService).findByAddressContaining(unit.getAddress());
    }

    @Test
    void whenFindByParentId_thenReturnsUnits() {
        List<Unit> units = Arrays.asList(unit);
        when(unitService.findByParentId(any(UUID.class))).thenReturn(units);

        ResponseEntity<List<Unit>> response = unitController.findByParentId(parentUnit.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(unitService).findByParentId(parentUnit.getId());
    }

    @Test
    void whenFindAll_thenReturnsAllUnits() {
        List<Unit> units = Arrays.asList(unit);
        when(unitService.findAll()).thenReturn(units);

        ResponseEntity<List<Unit>> response = unitController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(unitService).findAll();
    }
} 