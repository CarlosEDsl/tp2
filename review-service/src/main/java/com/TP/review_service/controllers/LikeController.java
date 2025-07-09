package com.TP.review_service.controllers;

import com.TP.review_service.models.Like;
import com.TP.review_service.security.AuthValidator;
import com.TP.review_service.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<Like> createLike(@RequestBody Like like) {

        AuthValidator.checkIfUserIsAuthorized(like.getUserId());

        Like createdLike = likeService.postLike(like);

        return new ResponseEntity<>(createdLike, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@RequestBody Like like) {

        AuthValidator.checkIfUserIsAuthorized(like.getUserId());

        likeService.removeLike(like);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Double> countLikes(@PathVariable("postId") UUID postId) {
        Double likeCount = likeService.countPostLikes(postId);
        return ResponseEntity.ok(likeCount);
    }

}
