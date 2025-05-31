package com.z7design.secured_guard.service;

import com.z7design.secured_guard.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(UUID userId, Notification notification) {
        try {
            messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                notification
            );
            log.info("Notification sent to user {}: {}", userId, notification.getTitle());
        } catch (Exception e) {
            log.error("Failed to send notification to user {}: {}", userId, e.getMessage());
        }
    }

    public void sendGlobalNotification(Notification notification) {
        try {
            messagingTemplate.convertAndSend("/topic/notifications", notification);
            log.info("Global notification sent: {}", notification.getTitle());
        } catch (Exception e) {
            log.error("Failed to send global notification: {}", e.getMessage());
        }
    }

    public void sendDashboardUpdate(Object data) {
        try {
            messagingTemplate.convertAndSend("/topic/dashboard", data);
            log.info("Dashboard update sent");
        } catch (Exception e) {
            log.error("Failed to send dashboard update: {}", e.getMessage());
        }
    }
}