package com.z7design.secured_guard.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUserId(Long userId);
    
    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);
    
    List<Notification> findByUserIdAndType(Long userId, NotificationType type);
    
    List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
} 