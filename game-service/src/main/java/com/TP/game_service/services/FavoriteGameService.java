package com.TP.game_service.services;

import com.TP.game_service.models.DTOs.FavoriteGameRequestDTO;
import com.TP.game_service.models.FavoriteGame;
import com.TP.game_service.repositories.FavoriteGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FavoriteGameService {
    private final FavoriteGameRepository favoriteGameRepository;

    public FavoriteGameService(FavoriteGameRepository favoriteGameRepository) {
        this.favoriteGameRepository = favoriteGameRepository;
    }

    public void saveNewFavoriteGame(FavoriteGameRequestDTO favoriteGame, UUID userId) {
        Optional<FavoriteGame> existingFavoriteGame = favoriteGameRepository.findByUserIdAndGameId(userId, favoriteGame.gameId());
        if(existingFavoriteGame.isEmpty()) {
            FavoriteGame newFavoriteGame = new FavoriteGame(
                    favoriteGame.gameId(),
                    userId
            );
            favoriteGameRepository.save(newFavoriteGame);
        }
    }

    public Optional<FavoriteGame> getFavoriteGameByGameIdAndUserId(int gameId, UUID userId) {
        return favoriteGameRepository.findByUserIdAndGameId(userId, gameId);
    }

    public List<FavoriteGame> getAllFavoriteGamesByUserId(UUID userId) {
        return favoriteGameRepository.findFavoriteGamesByUserId(userId);
    }

    public void deleteFavoriteGame(int gameId, UUID userId) {
        favoriteGameRepository.deleteByUserIdAndGameId(userId, gameId);
    }
}
