package com.TP.review_service.repositories;

import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByAuthorId(UUID authorId);

    @Query("SELECT p.rate FROM Post p WHERE p.gameId = :gameId")
    List<Rate> findRatingByGameId(@Param("gameId") UUID gameId);
}
