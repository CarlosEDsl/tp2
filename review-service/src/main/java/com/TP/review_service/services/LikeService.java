package com.TP.review_service.services;

import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.models.Like;
import com.TP.review_service.repositories.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    UpdateAverageCommand updateAverageCommand;

    public LikeService(LikeRepository likeRepository, UpdateAverageCommand updateAverageCommand) {
        this.likeRepository = likeRepository;
        this.updateAverageCommand = updateAverageCommand;
    }

    public Like postLike(Like like) {
        Like.LikeId likeId = new Like.LikeId(like.getUserId(), like.getPostId());
        Optional<Like> likeFound = this.likeRepository.findById(likeId);

        likeFound.ifPresent(lf -> {
            throw new IllegalStateException("Entidade já existe com esse ID!");
        });

        return likeRepository.save(like);
    }

    public void removeLike(Like like) {
        this.likeRepository.delete(like);
    }

    public Double countPostLikes(UUID postId) {
        return this.likeRepository.countByPostId(postId);
    }

}
