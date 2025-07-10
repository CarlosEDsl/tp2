package com.TP.review_service.rabbitmq;

import com.TP.review_service.models.DTO.CreateNotificationDTO;
import com.TP.review_service.models.DTO.SendNewGameRateAVGDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationSender {

    private final RabbitTemplate rabbitTemplate;

    public NotificationSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(CreateNotificationDTO createNotificationDTO) {
        try {
            rabbitTemplate.convertAndSend("notifications.exchange", "notifications.key", createNotificationDTO);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar notificação", e);
        }
    }

    public void sendNewGameAVG(SendNewGameRateAVGDTO sendNewGameRateAVGDTO) {
        try {
            rabbitTemplate.convertAndSend("games.exchange", "games.key", sendNewGameRateAVGDTO);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar gameRating", e);
        }
    }
}
