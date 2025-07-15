package com.TP.review_service.builders;

import com.TP.review_service.models.DTO.CreatePostDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class PostDirector {

    public Post constructFromDTO(CreatePostDTO dto) {
        return new PostBuilder()
                .id(UUID.randomUUID())  // gera novo ID
                .authorId(dto.authorId())
                .gameId(dto.gameId())
                .title(dto.title())
                .content(dto.content())
                .imageURL(dto.imageURL())
                .rate(Rate.fromValue(dto.rate()))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public Post constructDefault() {
        return new PostBuilder()
                .id(UUID.randomUUID())
                .authorId(UUID.randomUUID())
                .gameId(1L)
                .title("Título padrão")
                .content("Conteúdo exemplo")
                .rate(Rate.fromValue(3))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}

