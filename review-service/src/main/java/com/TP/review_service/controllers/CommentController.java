package com.TP.review_service.controllers;

import com.TP.review_service.models.Comment;
import com.TP.review_service.models.DTO.CreateCommentDTO;
import com.TP.review_service.security.AuthValidator;
import com.TP.review_service.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentDTO commentDTO) {

        AuthValidator.checkIfUserIsAuthorized(commentDTO.userId());

        Comment createdComment = commentService.postComment(commentDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") UUID id) {
        commentService.removeComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable("postId") UUID postId) {
        List<Comment> comments = commentService.getAllCommentsFromPost(postId);
        return ResponseEntity.ok(comments);
    }

}
