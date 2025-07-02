package com.TP.game_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "favorite_game")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteGame {
    public FavoriteGame(Long gameId, UUID userId) {
        this.gameId = gameId;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "game_id")
    private Long gameId;
    @Column(name = "user_id")
    private UUID userId;
}
