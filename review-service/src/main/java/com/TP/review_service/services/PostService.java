package com.TP.review_service.services;

import com.TP.review_service.builders.PostBuilder;
import com.TP.review_service.builders.PostDirector;
import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.commands.dispatcher.CommandDispatcher;
import com.TP.review_service.exceptions.custom.ResourceNotFoundException;
import com.TP.review_service.models.DTO.CreatePostDTO;
import com.TP.review_service.models.DTO.UpdatePostDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;
import com.TP.review_service.rabbitmq.NotificationSender;
import com.TP.review_service.repositories.PostRepository;
import com.TP.review_service.security.AuthValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final NotificationSender notificationSender;
    private final CommandDispatcher commandDispatcher;
    private final PostDirector postDirector;

    public PostService(PostRepository postRepository, NotificationSender notificationSender, CommandDispatcher commandDispatcher, PostDirector postDirector) {
        this.postRepository = postRepository;
        this.notificationSender = notificationSender;
        this.commandDispatcher = commandDispatcher;
        this.postDirector = postDirector;
    }

    public void updateGameAverage(UUID postId) {
        UpdateAverageCommand command = new UpdateAverageCommand(postId, postRepository, notificationSender);
        commandDispatcher.dispatch(command);
    }


    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post com ID " + id + " não encontrado"));
    }

    public List<Post> getPostsFromUser(UUID userId) {
        return postRepository.findAllByAuthorId(userId);
    }

    public List<Post> getNewestPosts() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable).getContent();
    }

    public Post createPost(CreatePostDTO postDTO) {
        AuthValidator.checkIfUserIsAuthorized(postDTO.authorId());

        Post newPost = this.postDirector.constructFromDTO(postDTO);
        Post postInserted = this.postRepository.save(newPost);

        this.updateGameAverage(postInserted.getId());

        return postInserted;
    }

    public Post updatePost(UUID id, UpdatePostDTO updatedPost) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        AuthValidator.checkIfUserIsAuthorized(existingPost.getAuthorId());

        if(existingPost.getRate().value != updatedPost.rate())
            this.updateGameAverage(id);

        existingPost.setTitle(updatedPost.title());
        existingPost.setContent(updatedPost.content());
        existingPost.setGameId(updatedPost.gameId());
        existingPost.setUpdatedAt(Instant.now());

        return postRepository.save(existingPost);
    }

    public void deletePost(UUID id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post com ID " + id + " não encontrado"));

        AuthValidator.checkIfUserIsAuthorized(existingPost.getAuthorId());
        this.updateGameAverage(id);

        postRepository.delete(existingPost);
    }

}
