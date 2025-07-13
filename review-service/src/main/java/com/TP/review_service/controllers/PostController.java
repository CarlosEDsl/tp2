package com.TP.review_service.controllers;

import com.TP.review_service.models.DTO.CreatePostDTO;
import com.TP.review_service.models.DTO.UpdatePostDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.security.AuthValidator;
import com.TP.review_service.services.PostService;
import com.TP.review_service.utils.AuthUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") UUID id) {
        Post post = postService.getPostById(id);

        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsFromUser(@PathVariable("userId") UUID userId) {
        List<Post> posts = postService.getPostsFromUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getNewestPosts() {
        List<Post> newestPosts = postService.getNewestPosts();
        return ResponseEntity.ok(newestPosts);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostDTO postDTO) {

        AuthValidator.checkIfUserIsAuthorized(postDTO.authorId());

        Post createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") UUID id, @RequestBody UpdatePostDTO updatedPost) {

        AuthValidator.checkIfUserIsAuthorized(id);

        Post post = postService.updatePost(id, updatedPost);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


}
