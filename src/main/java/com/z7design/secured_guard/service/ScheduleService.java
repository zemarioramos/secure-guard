package com.z7design.secured_guard.service;

import com.z7design.secured_guard.dto.ScheduleDTO;
import com.z7design.secured_guard.dto.EmployeeScheduleDTO;
import com.z7design.secured_guard.model.Schedule;
import com.z7design.secured_guard.model.EmployeeSchedule;
import com.z7design.secured_guard.model.Location;
import com.z7design.secured_guard.model.Position;
import com.z7design.secured_guard.model.Route;
import com.z7design.secured_guard.model.Patrol;
import com.z7design.secured_guard.repository.ScheduleRepository;
import com.z7design.secured_guard.repository.EmployeeScheduleRepository;
import com.z7design.secured_guard.repository.LocationRepository;
import com.z7design.secured_guard.repository.PositionRepository;
import com.z7design.secured_guard.repository.RouteRepository;
import com.z7design.secured_guard.repository.PatrolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeScheduleRepository employeeScheduleRepository;
    private final LocationRepository locationRepository;
    private final PositionRepository positionRepository;
    private final RouteRepository routeRepository;
    private final PatrolRepository patrolRepository;

    @Transactional
    public Schedule createSchedule(ScheduleDTO dto) {
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        
        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));
        
        Patrol patrol = patrolRepository.findById(dto.getPatrolId())
                .orElseThrow(() -> new EntityNotFoundException("Patrol not found"));

        Schedule schedule = new Schedule();
        schedule.setScheduleDate(dto.getScheduleDate());
        schedule.setShift(dto.getShift());
        schedule.setLocation(location);
        schedule.setStatus(dto.getStatus());
        schedule.setRoute(route);
        schedule.setPatrol(patrol);
        schedule.setObservations(dto.getObservations());

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public EmployeeSchedule createEmployeeSchedule(EmployeeScheduleDTO dto) {
        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        
        Position position = positionRepository.findById(dto.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));

        EmployeeSchedule employeeSchedule = new EmployeeSchedule();
        employeeSchedule.setSchedule(schedule);
        employeeSchedule.setPosition(position);
        employeeSchedule.setScheduleDate(dto.getScheduleDate());
        employeeSchedule.setShift(dto.getShift());
        employeeSchedule.setStatus(dto.getStatus());
        employeeSchedule.setObservations(dto.getObservations());

        return employeeScheduleRepository.save(employeeSchedule);
    }

    public List<Schedule> findByPeriod(LocalDate startDate, LocalDate endDate) {
        return scheduleRepository.findByScheduleDateBetween(startDate, endDate);
    }

    public List<EmployeeSchedule> findByScheduleId(UUID scheduleId) {
        return employeeScheduleRepository.findByScheduleId(scheduleId);
    }
} 