package com.TP.notifications.services;

import com.TP.notifications.exceptions.Custom.NotificationCreationException;
import com.TP.notifications.models.DTOs.CreateNotificationDTO;
import com.TP.notifications.models.Notification;
import com.TP.notifications.repositories.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNonReadNotificationsToUser(UUID receiver_id) {
        return this.notificationRepository.findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(receiver_id);
    }

    public List<Notification> getAllNotificationsToUser(UUID receiver_id) {
        return this.notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiver_id);
    }

    public void createNotification(CreateNotificationDTO dto) {
        Notification notification = this.notificationFromDTO(dto);
        System.out.println(notification);
        try {
            notificationRepository.save(notification);
        } catch (Exception e) {
            throw new NotificationCreationException("Erro ao criar notificação", e);
        }
    }

    private Notification notificationFromDTO(CreateNotificationDTO createNotificationDTO) {
        return new Notification(
                createNotificationDTO.receiverId(),
                createNotificationDTO.senderId(),
                createNotificationDTO.type(),
                createNotificationDTO.redirect()
        );
    }

    @Transactional
    public void markAsRead(UUID notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada"));
        n.setRead(true);
        notificationRepository.save(n);
    }



}
