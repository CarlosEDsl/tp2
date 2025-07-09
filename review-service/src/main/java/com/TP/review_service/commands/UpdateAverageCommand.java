package com.TP.review_service.commands;

import com.TP.review_service.models.DTO.SendNewGameRateAVGDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;
import com.TP.review_service.rabbitmq.NotificationSender;
import com.TP.review_service.repositories.PostRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UpdateAverageCommand implements Command {

    private final UUID postId;
    private final PostRepository postRepo;
    private final NotificationSender notificationSender;

    public UpdateAverageCommand(UUID postId, PostRepository postRepo, NotificationSender notificationSender) {
        this.postId = postId;
        this.postRepo = postRepo;
        this.notificationSender = notificationSender;
    }

    @Override
    public void execute() {
        Optional<Post> post = postRepo.findById(postId);

        List<Rate> rates = postRepo.findRatingByGameId(post.get().getGameId());
        Double avg = rates.stream()
                .filter(Objects::nonNull)
                .mapToDouble(r -> r.value)
                .average()
                .orElse(0);

        SendNewGameRateAVGDTO message = new SendNewGameRateAVGDTO(avg, post.get().getGameId());
        this.notificationSender.sendNewGameAVG(message);
    }
}
