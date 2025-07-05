package com.TP.review_service.services;

import com.TP.review_service.builders.PostBuilder;
import com.TP.review_service.models.DTO.CreatePostDTO;
import com.TP.review_service.models.DTO.NewestPostsDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));
    }

    public List<Post> getPostsFromUser(UUID userId) {
        return postRepository.findAllByAuthorId(userId);
    }

    public List<Post> getNewestPosts(NewestPostsDTO newestPostsDTO) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable).getContent();
    }

    public Post createPost(CreatePostDTO postDTO) {
        Post newPost = this.postFromDTO(postDTO);
        return postRepository.save(newPost);
    }

    public Post updatePost(UUID id, Post updatedPost) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setUpdatedAt(updatedPost.getUpdatedAt());

        return postRepository.save(existingPost);
    }

    public void deletePost(UUID id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));
        postRepository.delete(existingPost);
    }

    public Post postFromDTO(CreatePostDTO createPostDTO) {
        return new PostBuilder()
                .authorId(createPostDTO.authorId())
                .gameId(createPostDTO.gameId())
                .title(createPostDTO.title())
                .content(createPostDTO.content())
                .imageURL(createPostDTO.imageURL())
                .build();
    }
}
