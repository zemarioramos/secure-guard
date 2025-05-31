package com.z7design.secured_guard.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;
import com.z7design.secured_guard.repository.NotificationRepository;
import com.z7design.secured_guard.service.NotificationService;
import com.z7design.secured_guard.service.UserService;
import com.z7design.secured_guard.service.WebSocketNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final WebSocketNotificationService webSocketService;
    
    @Override
    @Transactional
    public Notification create(Notification notification) {
        validateNotification(notification);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setCreatedAt(LocalDateTime.now());
        
        Notification savedNotification = notificationRepository.save(notification);
        
        // Send real-time notification via WebSocket
        webSocketService.sendNotificationToUser(notification.getUser().getId(), savedNotification);
        
        log.info("Notification created and sent: {} for user {}", 
                savedNotification.getTitle(), notification.getUser().getId());
        
        return savedNotification;
    }
    
    @Override
    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = findById(id);
        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }
    
    @Override
    @Transactional
    public Notification markAsUnread(Long id) {
        Notification notification = findById(id);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setReadAt(null);
        return notificationRepository.save(notification);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        Notification notification = findById(id);
        notificationRepository.delete(notification);
    }
    
    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
    }
    
    @Override
    public List<Notification> findByUserId(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }
    
    @Override
    public List<Notification> findByUserIdAndStatus(UUID userId, NotificationStatus status) {
        return notificationRepository.findByUserIdAndStatus(userId, status);
    }
    
    @Override
    public List<Notification> findByUserIdAndType(UUID userId, NotificationType type) {
        return notificationRepository.findByUserIdAndType(userId, type);
    }
    
    @Override
    public List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return notificationRepository.findByCreatedAtBetween(startDate, endDate);
    }
    
    @Override
    public Page<Notification> findByUserId(UUID userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
    
    @Override
    public List<Notification> findUnreadByUserId(UUID userId) {
        return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }
    
    @Override
    public Long countUnreadByUserId(UUID userId) {
        return notificationRepository.countByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }
    
    @Override
    @Transactional
    public void createSystemNotification(String title, String message, NotificationType type) {
        // Create notification for all active users
        List<User> activeUsers = userService.findActiveUsers();
        
        for (User user : activeUsers) {
            Notification notification = Notification.builder()
                    .user(user)
                    .title(title)
                    .message(message)
                    .type(type)
                    .status(NotificationStatus.UNREAD)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            notificationRepository.save(notification);
            webSocketService.sendNotificationToUser(user.getId(), notification);
        }
        
        log.info("System notification sent to {} users: {}", activeUsers.size(), title);
    }
    
    @Override
    public List<Notification> getUnreadNotifications(UUID userId) {
        return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }
    
    @Override
    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
        for (Notification notification : notifications) {
            notification.setStatus(NotificationStatus.READ);
            notification.setReadAt(LocalDateTime.now());
        }
        notificationRepository.saveAll(notifications);
    }
    
    private void validateNotification(Notification notification) {
        if (notification.getUser() == null) {
            throw new IllegalArgumentException("User is required for notification");
        }
        if (notification.getTitle() == null || notification.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required for notification");
        }
        if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Message is required for notification");
        }
    }
}