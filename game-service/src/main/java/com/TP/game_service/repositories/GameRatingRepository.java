package com.TP.game_service.repositories;

import com.TP.game_service.models.GameRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRatingRepository extends JpaRepository<GameRating, UUID> {
}
