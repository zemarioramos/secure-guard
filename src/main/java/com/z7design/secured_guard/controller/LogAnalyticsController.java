package com.z7design.secured_guard.controller;

import com.z7design.secured_guard.model.UserActivityLog;
import com.z7design.secured_guard.model.ErrorLog;
import com.z7design.secured_guard.service.LogAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs/analytics")
public class LogAnalyticsController {

    private final LogAnalyticsService logAnalyticsService;

    @Autowired
    public LogAnalyticsController(LogAnalyticsService logAnalyticsService) {
        this.logAnalyticsService = logAnalyticsService;
    }

    // Endpoints de agregação
    @GetMapping("/activities/by-action")
    public ResponseEntity<Map<String, Long>> getActivityCountByAction() {
        return ResponseEntity.ok(logAnalyticsService.getActivityCountByAction());
    }

    @GetMapping("/errors/by-endpoint")
    public ResponseEntity<Map<String, Long>> getErrorCountByEndpoint() {
        return ResponseEntity.ok(logAnalyticsService.getErrorCountByEndpoint());
    }

    @GetMapping("/activities/by-user")
    public ResponseEntity<Map<String, Long>> getActivityCountByUser() {
        return ResponseEntity.ok(logAnalyticsService.getActivityCountByUser());
    }

    // Endpoints de estatísticas avançadas
    @GetMapping("/activities/stats/advanced")
    public ResponseEntity<Map<String, Object>> getAdvancedActivityStats() {
        return ResponseEntity.ok(logAnalyticsService.getAdvancedActivityStats());
    }

    @GetMapping("/errors/stats/advanced")
    public ResponseEntity<Map<String, Object>> getAdvancedErrorStats() {
        return ResponseEntity.ok(logAnalyticsService.getAdvancedErrorStats());
    }

    // Endpoints de exportação
    @GetMapping("/activities/export/csv")
    public ResponseEntity<String> exportActivitiesToCSV() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "activities.csv");
        return ResponseEntity.ok()
            .headers(headers)
            .body(logAnalyticsService.exportActivitiesToCSV());
    }

    @GetMapping("/activities/export/json")
    public ResponseEntity<String> exportActivitiesToJSON() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "activities.json");
        return ResponseEntity.ok()
            .headers(headers)
            .body(logAnalyticsService.exportActivitiesToJSON());
    }

    @GetMapping("/activities/export/excel")
    public ResponseEntity<byte[]> exportActivitiesToExcel() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "activities.xlsx");
        return ResponseEntity.ok()
            .headers(headers)
            .body(logAnalyticsService.exportActivitiesToExcel());
    }

    @GetMapping("/errors/export/csv")
    public ResponseEntity<String> exportErrorsToCSV() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "errors.csv");
        return ResponseEntity.ok()
            .headers(headers)
            .body(logAnalyticsService.exportErrorsToCSV());
    }

    @GetMapping("/errors/export/json")
    public ResponseEntity<String> exportErrorsToJSON() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "errors.json");
        return ResponseEntity.ok()
            .headers(headers)
            .body(logAnalyticsService.exportErrorsToJSON());
    }

    @GetMapping("/errors/export/excel")
    public ResponseEntity<byte[]> exportErrorsToExcel() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "errors.xlsx");
        return ResponseEntity.ok()
            .headers(headers)
            .body(logAnalyticsService.exportErrorsToExcel());
    }

    // Endpoints de filtro avançado
    @GetMapping("/activities/filter/advanced")
    public ResponseEntity<List<UserActivityLog>> getActivitiesWithAdvancedFilters(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String details,
            @RequestParam(required = false) Long minId,
            @RequestParam(required = false) Long maxId) {
        return ResponseEntity.ok(logAnalyticsService.getActivitiesWithAdvancedFilters(
            username, action, startDate, endDate, details, minId, maxId));
    }

    @GetMapping("/errors/filter/advanced")
    public ResponseEntity<List<ErrorLog>> getErrorsWithAdvancedFilters(
            @RequestParam(required = false) String endpoint,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) Long minId,
            @RequestParam(required = false) Long maxId) {
        return ResponseEntity.ok(logAnalyticsService.getErrorsWithAdvancedFilters(
            endpoint, startDate, endDate, message, minId, maxId));
    }
} 