package com.TP.notifications.repositories;

import com.TP.notifications.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(UUID receiverId);
    List<Notification> findByReceiverIdOrderByCreatedAtDesc(UUID receiverId);
}
