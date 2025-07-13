package com.TP.review_service.services;

import com.TP.review_service.builders.PostBuilder;
import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.exceptions.custom.ResourceNotFoundException;
import com.TP.review_service.models.DTO.CreatePostDTO;
import com.TP.review_service.models.DTO.UpdatePostDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;
import com.TP.review_service.rabbitmq.NotificationSender;
import com.TP.review_service.repositories.PostRepository;
import com.TP.review_service.security.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    public PostService(PostRepository postRepository, NotificationSender notificationSender) {
        this.postRepository = postRepository;
        this.notificationSender = notificationSender;
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
        Post newPost = this.postFromDTO(postDTO);
        Post postInserted = this.postRepository.save(newPost);

        UpdateAverageCommand updateAverageCommand = new UpdateAverageCommand(
                postInserted.getId(), this.postRepository, this.notificationSender);
        updateAverageCommand.execute();

        return postInserted;
    }

    public Post updatePost(UUID id, UpdatePostDTO updatedPost) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

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
        postRepository.delete(existingPost);
    }

    public Post postFromDTO(CreatePostDTO createPostDTO) {
        Rate rate = Rate.fromValue(createPostDTO.rate());

        return new PostBuilder()
                .authorId(createPostDTO.authorId())
                .gameId(createPostDTO.gameId())
                .title(createPostDTO.title())
                .content(createPostDTO.content())
                .imageURL(createPostDTO.imageURL())
                .rate(rate)
                .build();
    }
}
