package com.TP.notifications.controllers;

import com.TP.notifications.models.Notification;
import com.TP.notifications.services.NotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final RabbitTemplate rabbitTemplate;

    public NotificationController(NotificationService notificationService, RabbitTemplate rabbitTemplate) {
        this.notificationService = notificationService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("userId") UUID userId) {
        List<Notification> userNotifications = this.notificationService.getAllNotificationsToUser(userId);
        return ResponseEntity.ok(userNotifications);
    }

    @GetMapping("/newest/{userId}")
    public ResponseEntity<List<Notification>> getNewestUserNotifications(@PathVariable("userId") UUID userId) {
        List<Notification> userNotifications = this.notificationService.getNonReadNotificationsToUser(userId);
        return ResponseEntity.ok(userNotifications);
    }

    @PatchMapping("/read/{notfId}")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable("notfId") UUID notfId) {
        this.notificationService.markAsRead(notfId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/testRabbit")
    public ResponseEntity<Void> notifyRabbit() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("receiverId", UUID.fromString("e7b1f4a2-1234-4abc-9de6-987654321000"));
        payload.put("senderId", UUID.fromString("a3d6c5f7-5678-4def-8abc-123456789000"));
        payload.put("type", "NEW_MESSAGE");
        payload.put("redirect", "/chat/123");

        rabbitTemplate.convertAndSend("notifications.exchange", "notifications.key", payload);

        return ResponseEntity.noContent().build();
    }

}
