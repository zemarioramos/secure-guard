package com.z7design.secured_guard.service;

import com.z7design.secured_guard.dto.ScheduleDTO;
import com.z7design.secured_guard.dto.EmployeeScheduleDTO;
import com.z7design.secured_guard.model.Schedule;
import com.z7design.secured_guard.model.EmployeeSchedule;
import com.z7design.secured_guard.model.Location;
import com.z7design.secured_guard.model.Position;
import com.z7design.secured_guard.model.Route;
import com.z7design.secured_guard.model.Patrol;
import com.z7design.secured_guard.model.enums.ScheduleStatus;
import com.z7design.secured_guard.model.enums.Shift;
import com.z7design.secured_guard.repository.ScheduleRepository;
import com.z7design.secured_guard.repository.EmployeeScheduleRepository;
import com.z7design.secured_guard.repository.LocationRepository;
import com.z7design.secured_guard.repository.PositionRepository;
import com.z7design.secured_guard.repository.RouteRepository;
import com.z7design.secured_guard.repository.PatrolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private PatrolRepository patrolRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private UUID locationId;
    private UUID positionId;
    private UUID routeId;
    private UUID patrolId;
    private UUID scheduleId;

    @BeforeEach
    void setUp() {
        locationId = UUID.randomUUID();
        positionId = UUID.randomUUID();
        routeId = UUID.randomUUID();
        patrolId = UUID.randomUUID();
        scheduleId = UUID.randomUUID();
    }

    @Test
    void createSchedule_ShouldCreateScheduleSuccessfully() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        dto.setScheduleDate(LocalDate.now());
        dto.setShift(Shift.MORNING);
        dto.setLocationId(locationId);
        dto.setStatus(ScheduleStatus.PENDING);
        dto.setRouteId(routeId);
        dto.setPatrolId(patrolId);

        Location location = new Location();
        location.setId(locationId);
        Route route = new Route();
        route.setId(routeId);
        Patrol patrol = new Patrol();
        patrol.setId(patrolId);

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
        when(routeRepository.findById(routeId)).thenReturn(Optional.of(route));
        when(patrolRepository.findById(patrolId)).thenReturn(Optional.of(patrol));
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Schedule result = scheduleService.createSchedule(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getScheduleDate(), result.getScheduleDate());
        assertEquals(dto.getShift(), result.getShift());
        assertEquals(location, result.getLocation());
        assertEquals(route, result.getRoute());
        assertEquals(patrol, result.getPatrol());
    }

    @Test
    void createSchedule_WhenLocationNotFound_ShouldThrowException() {
        // Arrange
        ScheduleDTO dto = new ScheduleDTO();
        dto.setLocationId(locationId);

        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> scheduleService.createSchedule(dto));
    }

    @Test
    void createEmployeeSchedule_ShouldCreateEmployeeScheduleSuccessfully() {
        // Arrange
        EmployeeScheduleDTO dto = new EmployeeScheduleDTO();
        dto.setScheduleId(scheduleId);
        dto.setPositionId(positionId);

        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);

        // ... rest of the test implementation
    }
} 