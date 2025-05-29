package com.z7design.secured_guard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

import com.z7design.secured_guard.model.enums.ScheduleStatus;
import com.z7design.secured_guard.model.enums.Shift;

class EmployeeScheduleTest {

    private EmployeeSchedule employeeSchedule;
    private Schedule schedule;
    private Employee employee;
    private Position position;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
        schedule.setId(UUID.randomUUID());

        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Test Employee");

        position = new Position();
        position.setId(UUID.randomUUID());
        position.setName("Test Position");

        employeeSchedule = new EmployeeSchedule();
        employeeSchedule.setId(UUID.randomUUID());
        employeeSchedule.setSchedule(schedule);
        employeeSchedule.setEmployee(employee);
        employeeSchedule.setPosition(position);
        employeeSchedule.setScheduleDate(LocalDate.now());
        employeeSchedule.setShift(Shift.MORNING);
        employeeSchedule.setStatus(ScheduleStatus.PENDING);
    }

    @Test
    void whenCreateEmployeeSchedule_thenEmployeeScheduleIsCreated() {
        assertNotNull(employeeSchedule);
        assertEquals(schedule, employeeSchedule.getSchedule());
        assertEquals(employee, employeeSchedule.getEmployee());
        assertEquals(position, employeeSchedule.getPosition());
        assertEquals(LocalDate.now(), employeeSchedule.getScheduleDate());
        assertEquals(Shift.MORNING, employeeSchedule.getShift());
        assertEquals(ScheduleStatus.PENDING, employeeSchedule.getStatus());
    }

    @Test
    void whenUpdateShift_thenShiftIsUpdated() {
        employeeSchedule.setShift(Shift.AFTERNOON);
        assertEquals(Shift.AFTERNOON, employeeSchedule.getShift());
    }

    @Test
    void whenUpdateStatus_thenStatusIsUpdated() {
        employeeSchedule.setStatus(ScheduleStatus.CONFIRMED);
        assertEquals(ScheduleStatus.CONFIRMED, employeeSchedule.getStatus());
    }
} 