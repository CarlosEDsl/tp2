package com.TP.review_service.services;

import com.TP.review_service.models.DTO.NewestPostsDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.repositories.PostRepository;
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
        Optional<Post> postFound = this.postRepository.findById(id);
        if(postFound.isEmpty()){
            throw new RuntimeException("Post não encontrado");
        }
        return postFound.get();
    }

    public List<Post> getPostsFromUser(UUID userId) {
        return this.postRepository.findAllByAuthorId(userId);
    }

    public List<Post> getNewestPosts(NewestPostsDTO newestPostsDTO) {

    }

}
