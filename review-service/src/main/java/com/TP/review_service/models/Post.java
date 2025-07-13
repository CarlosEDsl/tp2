package com.TP.review_service.models;

import com.TP.review_service.builders.PostBuilder;
import com.TP.review_service.models.enums.Rate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID authorId;

    @Column(nullable = false)
    private long gameId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 1024)
    private String imageURL;

    @Column
    @Enumerated
    private Rate rate;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Post(PostBuilder builder) {
        this.id = builder.getId();
        this.authorId = builder.getAuthorId();
        this.gameId = builder.getGameId();
        this.title = builder.getTitle();
        this.content = builder.getContent();
        this.imageURL = builder.getImageURL();
        this.rate = builder.getRate();
        this.createdAt = builder.getCreatedAt();
        this.updatedAt = builder.getUpdatedAt();
    }
}
