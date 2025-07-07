package com.TP.notifications.messaging;

import com.TP.notifications.models.DTOs.CreateNotificationDTO;
import com.TP.notifications.services.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class NotificationEventHandler {

    private final NotificationService notificationService;

    public NotificationEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "notifications", containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(CreateNotificationDTO createNotificationDTO) {
        notificationService.createNotification(createNotificationDTO);
    }

}
