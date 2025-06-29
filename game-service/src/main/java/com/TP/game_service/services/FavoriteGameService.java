package com.TP.game_service.services;

import com.TP.game_service.models.GameExtraInfoAdapted;
import com.TP.game_service.services.facade.ExternalApiUrlsFacade;
import com.TP.game_service.models.DTOs.FavoriteGameRequestDTO;
import com.TP.game_service.models.FavoriteGame;
import com.TP.game_service.repositories.FavoriteGameRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteGameService {
    private final FavoriteGameRepository favoriteGameRepository;
    private final CatalogoService catalogoService;

    public void saveNewFavoriteGame(Long gameId, UUID userId) {
        GameExtraInfoAdapted gameInfo = catalogoService.getGameById(gameId);

        if (gameInfo == null) {
            System.out.println("Erro na resposta: Nenhuma resposta recebida.");
            return;
        }

        boolean exists = favoriteGameRepository
                .findByUserIdAndGameId(userId, gameId)
                .isPresent();

        if (!exists) {
            System.out.println(userId);
            FavoriteGame newFavoriteGame = new FavoriteGame(gameId, userId);
            System.out.println(gameId);
            favoriteGameRepository.save(newFavoriteGame);
        }
    }

    public Optional<FavoriteGame> getFavoriteGameByGameIdAndUserId(Long gameId, UUID userId) {
        return favoriteGameRepository.findByUserIdAndGameId(userId, gameId);
    }

    public List<FavoriteGame> getAllFavoriteGamesByUserId(UUID userId) {
        return favoriteGameRepository.findFavoriteGamesByUserId(userId);
    }

    public void deleteFavoriteGame(Long gameId, UUID userId) {
        favoriteGameRepository.deleteByUserIdAndGameId(userId, gameId);
    }
}
