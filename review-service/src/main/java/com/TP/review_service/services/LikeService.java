package com.TP.review_service.services;

import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.exceptions.custom.BusinessRuleException;
import com.TP.review_service.exceptions.custom.ResourceNotFoundException;
import com.TP.review_service.models.DTO.CreateNotificationDTO;
import com.TP.review_service.models.Like;
import com.TP.review_service.models.Post;
import com.TP.review_service.rabbitmq.NotificationSender;
import com.TP.review_service.repositories.LikeRepository;
import com.TP.review_service.repositories.PostRepository;
import com.TP.review_service.security.AuthValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final NotificationSender notificationSender;

    public LikeService(LikeRepository likeRepository, NotificationSender notificationSender, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.notificationSender = notificationSender;
        this.postRepository = postRepository;
    }

    public Like postLike(Like like) {
        Like.LikeId likeId = new Like.LikeId(like.getUserId(), like.getPostId());
        Optional<Like> likeFound = this.likeRepository.findById(likeId);
        System.out.println(likeFound);

        likeFound.ifPresent(lf -> {
            throw new BusinessRuleException("User already liked this post.");
        });

        this.notificateLike(like.getUserId(), like.getPostId());

        return likeRepository.save(like);
    }

    public void removeLike(Like like) {
        AuthValidator.checkIfUserIsAuthorized(like.getUserId());

        this.likeRepository.delete(like);
    }

    public Double countPostLikes(UUID postId) {
        return this.likeRepository.countByPostId(postId);
    }

    private void notificateLike(UUID senderId, UUID postId) {
        Optional<Post> post = this.postRepository.findById(postId);

        if(post.isEmpty()) {
            throw new ResourceNotFoundException("Post not found");
        }

        UUID receiverId = post.get().getAuthorId();

        CreateNotificationDTO notification = new CreateNotificationDTO(receiverId, senderId, "Like", "/"+postId);

        this.notificationSender.sendNotification(notification);
    }

    public boolean isPostLikedByUser(UUID userId, UUID postId) {
        Optional<Like> like = this.likeRepository.findLikeByUserIdAndPostId(userId, postId);
        return like.isPresent();
    }

}
