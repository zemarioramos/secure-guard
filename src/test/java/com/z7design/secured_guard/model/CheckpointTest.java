package com.z7design.secured_guard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

class CheckpointTest {

    private Checkpoint checkpoint;
    private Route route;
    private Location location;
    private Patrol patrol;

    @BeforeEach
    void setUp() {
        route = new Route();
        route.setId(UUID.randomUUID());
        route.setName("Test Route");

        location = Location.builder()
                .id(UUID.randomUUID())
                .name("Test Location")
                .build();

        patrol = new Patrol();
        patrol.setId(UUID.randomUUID());
        patrol.setRoute(route);

        checkpoint = Checkpoint.builder()
                .id(UUID.randomUUID())
                .route(route)
                .location(location)
                .patrol(patrol)
                .checkTime(LocalDateTime.now())
                .observations("Test observations")
                .build();
    }

    @Test
    void whenCreateCheckpoint_thenCheckpointIsCreated() {
        assertNotNull(checkpoint);
        assertEquals(route, checkpoint.getRoute());
        assertEquals(location, checkpoint.getLocation());
        assertEquals(patrol, checkpoint.getPatrol());
        assertNotNull(checkpoint.getCheckTime());
        assertEquals("Test observations", checkpoint.getObservations());
    }

    @Test
    void whenUpdateCheckTime_thenCheckTimeIsUpdated() {
        LocalDateTime newCheckTime = LocalDateTime.now().plusHours(1);
        checkpoint.setCheckTime(newCheckTime);
        assertEquals(newCheckTime, checkpoint.getCheckTime());
    }

    @Test
    void whenUpdateObservations_thenObservationsAreUpdated() {
        String newObservations = "Updated observations";
        checkpoint.setObservations(newObservations);
        assertEquals(newObservations, checkpoint.getObservations());
    }

    @Test
    void whenUpdateLocation_thenLocationIsUpdated() {
        Location newLocation = Location.builder()
                .id(UUID.randomUUID())
                .name("New Location")
                .build();

        checkpoint.setLocation(newLocation);
        assertEquals(newLocation, checkpoint.getLocation());
    }
} 