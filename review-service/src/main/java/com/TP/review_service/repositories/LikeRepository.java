package com.TP.review_service.repositories;

import com.TP.review_service.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, Like.LikeId> {

    Double countByPostId(UUID postId);

    Optional<Like> findLikeByUserIdAndPostId(UUID userId, UUID postId);

}
