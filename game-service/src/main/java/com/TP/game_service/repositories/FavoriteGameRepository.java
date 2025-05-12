package com.TP.game_service.repositories;

import com.TP.game_service.models.FavoriteGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteGameRepository extends JpaRepository<FavoriteGame, UUID> {
    Optional<FavoriteGame> findByUserIdAndGameId(UUID userId, Long gameId);

    List<FavoriteGame> findFavoriteGamesByUserId(UUID userId);

    void deleteByUserIdAndGameId(UUID userId, int gameId);
}
