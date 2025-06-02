package com.z7design.secured_guard.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    
    List<Notification> findByUserId(UUID userId);
    
    List<Notification> findByUserIdAndStatus(UUID userId, NotificationStatus status);
    
    List<Notification> findByUserIdAndType(UUID userId, NotificationType type);
    
    List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Custom methods
    Page<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
    
    Page<Notification> findByUserIdAndStatusOrderByCreatedAtDesc(UUID userId, NotificationStatus status, Pageable pageable);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.status = :status")
    Long countByUserIdAndStatus(@Param("userId") UUID userId, @Param("status") NotificationStatus status);
    
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.status = 'UNREAD' ORDER BY n.createdAt DESC")
    List<Notification> findUnreadNotificationsByUserId(@Param("userId") UUID userId);
    
    @Modifying
    @Query("UPDATE Notification n SET n.status = 'READ', n.readAt = :readAt WHERE n.user.id = :userId AND n.status = 'UNREAD'")
    int markAllAsReadByUserId(@Param("userId") UUID userId, @Param("readAt") LocalDateTime readAt);
    
    @Query("SELECT n FROM Notification n WHERE n.type = :type AND n.createdAt >= :since ORDER BY n.createdAt DESC")
    List<Notification> findRecentNotificationsByType(@Param("type") NotificationType type, @Param("since") LocalDateTime since);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.createdAt >= :since")
    Long countNotificationsSince(@Param("since") LocalDateTime since);
    
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.createdAt < :before")
    int deleteOldNotifications(@Param("before") LocalDateTime before);
}