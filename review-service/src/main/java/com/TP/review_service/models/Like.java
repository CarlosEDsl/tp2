package com.TP.review_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Like.LikeId.class)
public class Like {

    @Id
    @Column(nullable = false)
    private UUID userId;

    @Id
    @Column(nullable = false)
    private UUID postId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeId implements Serializable {
        private UUID userId;
        private UUID postId;
    }
}