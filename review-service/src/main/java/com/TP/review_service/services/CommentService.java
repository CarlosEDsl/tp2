package com.TP.review_service.services;

import com.TP.review_service.models.Comment;
import com.TP.review_service.models.DTO.CreateCommentDTO;
import com.TP.review_service.repositories.CommentRepository;
import com.TP.review_service.security.AuthValidator;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment postComment(CreateCommentDTO comment) {
        Comment newComment = commentFromDTO(comment);
        return this.commentRepository.save(newComment);
    }

    public void removeComment(UUID commentId) {
        Optional<Comment> commentFound = this.commentRepository.findById(commentId);
        if(commentFound.isEmpty()) {
            throw new ResolutionException("Comment not found in id: "+ commentId);
        };

        AuthValidator.checkIfUserIsAuthorized(commentFound.get().getUserId());

        this.commentRepository.delete(commentFound.get());
    }

    public List<Comment> getAllCommentsFromPost(UUID postId) {
        return this.commentRepository.findAllByPostId(postId);
    }

    private Comment commentFromDTO(CreateCommentDTO createCommentDTO) {
        return new Comment(createCommentDTO.postId(),
                    createCommentDTO.userId(),
                    createCommentDTO.content());
    }

}
