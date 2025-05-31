package com.z7design.secured_guard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;
import com.z7design.secured_guard.repository.NotificationRepository;
import com.z7design.secured_guard.model.Contract;
import com.z7design.secured_guard.model.JobVacancy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    
    @Transactional
    public Notification create(Notification notification) {
        validateNotification(notification);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = findById(id);
        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public Notification markAsUnread(Long id) {
        Notification notification = findById(id);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setReadAt(null);
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public void delete(Long id) {
        Notification notification = findById(id);
        notificationRepository.delete(notification);
    }
    
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
    }
    
    public List<Notification> findByUserId(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }
    
    public List<Notification> findByUserIdAndStatus(UUID userId, NotificationStatus status) {
        return notificationRepository.findByUserIdAndStatus(userId, status);
    }
    
    public List<Notification> findByUserIdAndType(UUID userId, NotificationType type) {
        return notificationRepository.findByUserIdAndType(userId, type);
    }
    
    public List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return notificationRepository.findByCreatedAtBetween(startDate, endDate);
    }
    
    private void validateNotification(Notification notification) {
        if (notification.getUser() == null) {
            throw new IllegalArgumentException("User is required");
        }
        
        if (notification.getType() == null) {
            throw new IllegalArgumentException("Notification type is required");
        }
        
        if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification message is required");
        }
    }
    
    public void notifyContractCreated(Contract contract) {
        // TODO: Implementar notificações para diferentes setores
        // - RH
        // - Administrativo
        // - Operacional
        // - Setor de Contratos
    }
    
    public void notifyContractUpdated(Contract contract) {
        // TODO: Implementar notificações para diferentes setores
    }
    
    public void notifyContractExpiring(Contract contract) {
        // TODO: Implementar notificações para diferentes setores
    }
    
    public void notifyProposalCreated(Contract contract) {
        // TODO: Implementar notificações para diferentes setores
    }
    
    public void notifyProposalUpdated(Contract contract) {
        // TODO: Implementar notificações para diferentes setores
    }
    
    public void notifyNewJobVacancy(JobVacancy jobVacancy) {
        Notification notification = Notification.builder()
                .type(NotificationType.JOB_VACANCY)
                .title("Nova Vaga Disponível")
                .message("Uma nova vaga foi publicada: " + jobVacancy.getTitle())
                .status(NotificationStatus.UNREAD)
                .build();
        
        notificationRepository.save(notification);
    }
} 