package com.TP.review_service.builders;

import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;

import java.time.Instant;
import java.util.UUID;

public class PostBuilder {

    private UUID id;
    private UUID authorId;
    private long gameId;
    private String title;
    private String content;
    private String imageURL;
    private Rate rate;
    private Instant createdAt;
    private Instant updatedAt;

    public PostBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public PostBuilder authorId(UUID authorId) {
        this.authorId = authorId;
        return this;
    }

    public PostBuilder gameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    public PostBuilder title(String title) {
        this.title = title;
        return this;
    }

    public PostBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostBuilder imageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public PostBuilder rate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public PostBuilder createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PostBuilder updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Post build() {
        return new Post(this);
    }

    public UUID getId() { return id; }
    public UUID getAuthorId() { return authorId; }
    public long getGameId() { return gameId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageURL() { return imageURL; }
    public Rate getRate() { return rate; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
