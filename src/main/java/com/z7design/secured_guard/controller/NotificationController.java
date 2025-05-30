package com.z7design.secured_guard.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;
import com.z7design.secured_guard.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService notificationService;
    
    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.create(notification));
    }
    
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }
    
    @PutMapping("/{id}/unread")
    public ResponseEntity<Notification> markAsUnread(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsUnread(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Notification> findById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.findById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.findByUserId(userId));
    }
    
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Notification>> findByUserIdAndStatus(
            @PathVariable UUID userId,
            @PathVariable NotificationStatus status) {
        return ResponseEntity.ok(notificationService.findByUserIdAndStatus(userId, status));
    }
    
    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<Notification>> findByUserIdAndType(
            @PathVariable UUID userId,
            @PathVariable NotificationType type) {
        return ResponseEntity.ok(notificationService.findByUserIdAndType(userId, type));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Notification>> findByCreatedAtBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(notificationService.findByCreatedAtBetween(startDate, endDate));
    }
} 