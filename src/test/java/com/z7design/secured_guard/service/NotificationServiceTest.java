package com.z7design.secured_guard.service;

import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;
import com.z7design.secured_guard.repository.NotificationRepository;
import com.z7design.secured_guard.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserService userService;

    @Mock
    private WebSocketNotificationService webSocketService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private User testUser;
    private Notification testNotification;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .build();

        testNotification = Notification.builder()
                .id(UUID.randomUUID())
                .user(testUser)
                .title("Test Notification")
                .message("Test message")
                .type(NotificationType.INFO)
                .status(NotificationStatus.UNREAD)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createNotification_Success() {
        // Given
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        // When
        Notification result = notificationService.create(testNotification);

        // Then
        assertNotNull(result);
        assertEquals(testNotification.getTitle(), result.getTitle());
        assertEquals(NotificationStatus.UNREAD, result.getStatus());
        verify(notificationRepository).save(any(Notification.class));
        verify(webSocketService).sendNotificationToUser(eq(testUser.getId()), any(Notification.class));
    }

    @Test
    void createNotification_InvalidUser_ThrowsException() {
        // Given
        testNotification.setUser(null);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.create(testNotification);
        });
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void findUnreadByUserId_Success() {
        // Given
        List<Notification> unreadNotifications = Arrays.asList(testNotification);
        when(notificationRepository.findUnreadNotificationsByUserId(testUser.getId()))
                .thenReturn(unreadNotifications);

        // When
        List<Notification> result = notificationService.findUnreadByUserId(testUser.getId());

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNotification.getTitle(), result.get(0).getTitle());
    }

    @Test
    void countUnreadByUserId_Success() {
        // Given
        when(notificationRepository.countByUserIdAndStatus(testUser.getId(), NotificationStatus.UNREAD))
                .thenReturn(5L);

        // When
        Long count = notificationService.countUnreadByUserId(testUser.getId());

        // Then
        assertEquals(5L, count);
    }

    @Test
    void markAllAsRead_Success() {
        // Given
        when(notificationRepository.markAllAsReadByUserId(eq(testUser.getId()), any(LocalDateTime.class)))
                .thenReturn(3);

        // When
        notificationService.markAllAsRead(testUser.getId());

        // Then
        verify(notificationRepository).markAllAsReadByUserId(eq(testUser.getId()), any(LocalDateTime.class));
    }
}