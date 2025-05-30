package com.z7design.secured_guard.controller;

import com.z7design.secured_guard.model.UserActivityLog;
import com.z7design.secured_guard.model.ErrorLog;
import com.z7design.secured_guard.repository.UserActivityLogRepository;
import com.z7design.secured_guard.repository.ErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs/test")
public class LogTestController {

    private final UserActivityLogRepository userActivityLogRepository;
    private final ErrorLogRepository errorLogRepository;

    @Autowired
    public LogTestController(UserActivityLogRepository userActivityLogRepository,
                           ErrorLogRepository errorLogRepository) {
        this.userActivityLogRepository = userActivityLogRepository;
        this.errorLogRepository = errorLogRepository;
    }

    @PostMapping("/activity")
    public ResponseEntity<UserActivityLog> createActivityLog(
            @RequestParam String username,
            @RequestParam String action,
            @RequestParam String details) {
        
        UserActivityLog log = new UserActivityLog();
        log.setUsername(username);
        log.setAction(action);
        log.setDetails(details);
        
        UserActivityLog savedLog = userActivityLogRepository.save(log);
        return ResponseEntity.ok(savedLog);
    }

    @PostMapping("/error")
    public ResponseEntity<ErrorLog> createErrorLog(
            @RequestParam String message,
            @RequestParam String endpoint) {
        
        ErrorLog log = new ErrorLog();
        log.setMessage(message);
        log.setEndpoint(endpoint);
        log.setStackTrace("Stack trace de teste");
        
        ErrorLog savedLog = errorLogRepository.save(log);
        return ResponseEntity.ok(savedLog);
    }

    @GetMapping("/activities/paged")
    public ResponseEntity<Page<UserActivityLog>> getActivitiesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<UserActivityLog> logs = userActivityLogRepository.findAll(pageable);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/errors/paged")
    public ResponseEntity<Page<ErrorLog>> getErrorsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<ErrorLog> logs = errorLogRepository.findAll(pageable);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/activities/filter")
    public ResponseEntity<List<UserActivityLog>> getActivitiesFiltered(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        List<UserActivityLog> logs = userActivityLogRepository.findAll().stream()
            .filter(log -> username == null || log.getUsername().equals(username))
            .filter(log -> action == null || log.getAction().equals(action))
            .filter(log -> startDate == null || !log.getTimestamp().isBefore(startDate))
            .filter(log -> endDate == null || !log.getTimestamp().isAfter(endDate))
            .toList();
        
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/errors/filter")
    public ResponseEntity<List<ErrorLog>> getErrorsFiltered(
            @RequestParam(required = false) String endpoint,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        List<ErrorLog> logs = errorLogRepository.findAll().stream()
            .filter(log -> endpoint == null || log.getEndpoint().equals(endpoint))
            .filter(log -> startDate == null || !log.getTimestamp().isBefore(startDate))
            .filter(log -> endDate == null || !log.getTimestamp().isAfter(endDate))
            .toList();
        
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/activities/stats")
    public ResponseEntity<Map<String, Object>> getActivityStats() {
        long totalCount = userActivityLogRepository.count();
        List<UserActivityLog> recentLogs = userActivityLogRepository.findAll().stream()
            .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
            .limit(5)
            .toList();
        
        return ResponseEntity.ok(Map.of(
            "totalCount", totalCount,
            "recentLogs", recentLogs
        ));
    }

    @GetMapping("/errors/stats")
    public ResponseEntity<Map<String, Object>> getErrorStats() {
        long totalCount = errorLogRepository.count();
        List<ErrorLog> recentErrors = errorLogRepository.findAll().stream()
            .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
            .limit(5)
            .toList();
        
        return ResponseEntity.ok(Map.of(
            "totalCount", totalCount,
            "recentErrors", recentErrors
        ));
    }

    @GetMapping("/activities/{id}")
    public ResponseEntity<UserActivityLog> getActivityById(@PathVariable Long id) {
        return userActivityLogRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/errors/{id}")
    public ResponseEntity<ErrorLog> getErrorById(@PathVariable UUID id) {
        return errorLogRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activities/count")
    public ResponseEntity<Long> getActivityCount() {
        long count = userActivityLogRepository.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/errors/count")
    public ResponseEntity<Long> getErrorCount() {
        long count = errorLogRepository.count();
        return ResponseEntity.ok(count);
    }
} 