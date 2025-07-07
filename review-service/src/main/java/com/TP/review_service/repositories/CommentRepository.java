package com.TP.review_service.repositories;

import com.TP.review_service.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByPostId(UUID postId);
}
