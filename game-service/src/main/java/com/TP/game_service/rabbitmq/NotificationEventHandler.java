package com.TP.game_service.rabbitmq;

import com.TP.game_service.models.GameRating;
import com.TP.game_service.services.CatalogoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class NotificationEventHandler {

    private final CatalogoService catalogoService;

    public NotificationEventHandler(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @RabbitListener(queues = "games", containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(GameRating gameRating) {
        catalogoService.insertNewGameAVG(gameRating);
    }

}
