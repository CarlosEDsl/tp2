package com.TP.game_service.services;

import com.TP.game_service.models.GameExtraInfoAdapted;
import com.TP.game_service.models.FavoriteGame;
import com.TP.game_service.repositories.FavoriteGameRepository;
import com.TP.game_service.util.customExceptions.GameAlreadyFavoritedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FavoriteGameService {
    @Autowired
    private FavoriteGameRepository favoriteGameRepository;
    @Autowired
    private CatalogoService catalogoService;

    public FavoriteGame saveNewFavoriteGame(Long gameId, UUID userId) throws Exception {
        try {
            GameExtraInfoAdapted gameInfo = catalogoService.getGameById(gameId);

            if (gameInfo == null) {
                return null;
            }

            boolean exists = favoriteGameRepository
                    .findByUserIdAndGameId(userId, gameId)
                    .isPresent();

            if (!exists) {
                FavoriteGame newFavoriteGame = new FavoriteGame(gameId, userId);
                favoriteGameRepository.save(newFavoriteGame);
                return newFavoriteGame;
            } else {
                throw new GameAlreadyFavoritedException("Jogo já está nos seus favoritos");
            }
        }
        catch (Exception e) {
            throw e;
        }
    }

    public Optional<FavoriteGame> getFavoriteGameByGameIdAndUserId(Long gameId, UUID userId) {
        return favoriteGameRepository.findByUserIdAndGameId(userId, gameId);
    }

    public List<FavoriteGame> getAllFavoriteGamesByUserId(UUID userId) {
        return favoriteGameRepository.findFavoriteGamesByUserId(userId);
    }

    public boolean deleteFavoriteGame(Long gameId, UUID userId) {
        Optional<FavoriteGame> exists = favoriteGameRepository.findByUserIdAndGameId(userId, gameId);
        if(exists.isPresent()) {
            favoriteGameRepository.delete(exists.get());
            return true;
        } else {
            return false;
        }
    }
}
