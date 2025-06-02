package com.z7design.secured_guard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.JobVacancy;
import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;

public interface NotificationService {
    Notification create(Notification notification);
    Notification markAsRead(UUID id);
    Notification markAsUnread(UUID id);
    void delete(UUID id);
    Notification findById(UUID id);
    List<Notification> findByUserId(UUID userId);
    List<Notification> findByUserIdAndStatus(UUID userId, NotificationStatus status);
    List<Notification> findByUserIdAndType(UUID userId, NotificationType type);
    List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Page<Notification> findByUserId(UUID userId, Pageable pageable);
    List<Notification> findUnreadByUserId(UUID userId);
    Long countUnreadByUserId(UUID userId);
    void createSystemNotification(String title, String message, NotificationType type);
    List<Notification> getUnreadNotifications(UUID userId);
    void markAllAsRead(UUID userId);
    Notification save(Notification notification);
    
    // Business specific notifications
    void notifyContractCreated(Contract contract);
    void notifyContractUpdated(Contract contract);
    void notifyContractExpiring(Contract contract);
    void notifyProposalCreated(Contract contract);
    void notifyProposalUpdated(Contract contract);
    void notifyNewJobVacancy(JobVacancy jobVacancy);
} 