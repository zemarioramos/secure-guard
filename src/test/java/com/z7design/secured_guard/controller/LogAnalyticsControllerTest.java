package com.z7design.secured_guard.controller;

import com.z7design.secured_guard.model.UserActivityLog;
import com.z7design.secured_guard.model.ErrorLog;
import com.z7design.secured_guard.service.LogAnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogAnalyticsControllerTest {

    @Mock
    private LogAnalyticsService logAnalyticsService;

    @InjectMocks
    private LogAnalyticsController logAnalyticsController;

    private UserActivityLog activityLog;
    private ErrorLog errorLog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        activityLog = new UserActivityLog();
        activityLog.setId(1L);
        activityLog.setUsername("testUser");
        activityLog.setAction("TEST_ACTION");
        activityLog.setDetails("Test details");
        activityLog.setTimestamp(LocalDateTime.now());

        errorLog = new ErrorLog();
        errorLog.setId(1L);
        errorLog.setMessage("Test error");
        errorLog.setEndpoint("/api/test");
        errorLog.setStackTrace("Test stack trace");
        errorLog.setTimestamp(LocalDateTime.now());
    }

    @Test
    void getActivityCountByAction_ShouldReturnCorrectResponse() {
        Map<String, Long> expectedCounts = Map.of("TEST_ACTION", 1L);
        when(logAnalyticsService.getActivityCountByAction()).thenReturn(expectedCounts);

        ResponseEntity<Map<String, Long>> response = logAnalyticsController.getActivityCountByAction();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedCounts, response.getBody());
    }

    @Test
    void getErrorCountByEndpoint_ShouldReturnCorrectResponse() {
        Map<String, Long> expectedCounts = Map.of("/api/test", 1L);
        when(logAnalyticsService.getErrorCountByEndpoint()).thenReturn(expectedCounts);

        ResponseEntity<Map<String, Long>> response = logAnalyticsController.getErrorCountByEndpoint();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedCounts, response.getBody());
    }

    @Test
    void getActivityCountByUser_ShouldReturnCorrectResponse() {
        Map<String, Long> expectedCounts = Map.of("testUser", 1L);
        when(logAnalyticsService.getActivityCountByUser()).thenReturn(expectedCounts);

        ResponseEntity<Map<String, Long>> response = logAnalyticsController.getActivityCountByUser();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedCounts, response.getBody());
    }

    @Test
    void getAdvancedActivityStats_ShouldReturnCorrectResponse() {
        Map<String, Object> expectedStats = Map.of(
            "totalCount", 1L,
            "activitiesByHour", Map.of(12, 1L),
            "topActions", Map.of("TEST_ACTION", 1L)
        );
        when(logAnalyticsService.getAdvancedActivityStats()).thenReturn(expectedStats);

        ResponseEntity<Map<String, Object>> response = logAnalyticsController.getAdvancedActivityStats();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedStats, response.getBody());
    }

    @Test
    void getAdvancedErrorStats_ShouldReturnCorrectResponse() {
        Map<String, Object> expectedStats = Map.of(
            "totalCount", 1L,
            "errorsByEndpoint", Map.of("/api/test", 1L),
            "errorsByHour", Map.of(12, 1L)
        );
        when(logAnalyticsService.getAdvancedErrorStats()).thenReturn(expectedStats);

        ResponseEntity<Map<String, Object>> response = logAnalyticsController.getAdvancedErrorStats();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedStats, response.getBody());
    }

    @Test
    void exportActivitiesToCSV_ShouldReturnCorrectResponse() {
        String expectedCsv = "ID,Username,Action,Details,Timestamp\n1,testUser,TEST_ACTION,Test details,2024-01-01T12:00:00";
        when(logAnalyticsService.exportActivitiesToCSV()).thenReturn(expectedCsv);

        ResponseEntity<String> response = logAnalyticsController.exportActivitiesToCSV();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedCsv, response.getBody());
        assertEquals("attachment; filename=activities.csv", response.getHeaders().getFirst("Content-Disposition"));
    }

    @Test
    void exportErrorsToCSV_ShouldReturnCorrectResponse() {
        String expectedCsv = "ID,Message,Endpoint,StackTrace,Timestamp\n1,Test error,/api/test,Test stack trace,2024-01-01T12:00:00";
        when(logAnalyticsService.exportErrorsToCSV()).thenReturn(expectedCsv);

        ResponseEntity<String> response = logAnalyticsController.exportErrorsToCSV();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedCsv, response.getBody());
        assertEquals("attachment; filename=errors.csv", response.getHeaders().getFirst("Content-Disposition"));
    }

    @Test
    void getActivitiesWithAdvancedFilters_ShouldReturnCorrectResponse() {
        List<UserActivityLog> expectedLogs = Arrays.asList(activityLog);
        when(logAnalyticsService.getActivitiesWithAdvancedFilters(
            anyString(), anyString(), any(), any(), anyString(), any(), any()))
            .thenReturn(expectedLogs);

        ResponseEntity<List<UserActivityLog>> response = logAnalyticsController.getActivitiesWithAdvancedFilters(
            "testUser", "TEST_ACTION", null, null, null, null, null);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedLogs, response.getBody());
    }

    @Test
    void getErrorsWithAdvancedFilters_ShouldReturnCorrectResponse() {
        List<ErrorLog> expectedLogs = Arrays.asList(errorLog);
        when(logAnalyticsService.getErrorsWithAdvancedFilters(
            anyString(), any(), any(), anyString(), any(), any()))
            .thenReturn(expectedLogs);

        ResponseEntity<List<ErrorLog>> response = logAnalyticsController.getErrorsWithAdvancedFilters(
            "/api/test", null, null, null, null, null);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedLogs, response.getBody());
    }
} 