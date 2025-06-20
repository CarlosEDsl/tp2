package com.TP.game_service.services;

import com.TP.game_service.facade.RawgApiFacade;
import com.TP.game_service.models.DTOs.FavoriteGameRequestDTO;
import com.TP.game_service.models.FavoriteGame;
import com.TP.game_service.repositories.FavoriteGameRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FavoriteGameService {
    private final FavoriteGameRepository favoriteGameRepository;
    private final RawgApiFacade rawgApiFacade;

    public FavoriteGameService(FavoriteGameRepository favoriteGameRepository, RawgApiFacade rawgApiFacade) {
        this.favoriteGameRepository = favoriteGameRepository;
        this.rawgApiFacade = rawgApiFacade;
    }

    public void saveNewFavoriteGame(FavoriteGameRequestDTO favoriteGame, UUID userId) {
        String response = rawgApiFacade.getGameById(favoriteGame.gameId());

        if (response == null) {
            System.out.println("Erro na resposta: Nenhuma resposta recebida.");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            if (root.has("detail") && "Not found.".equals(root.get("detail").asText())) {
                System.out.println("Erro: Recurso não encontrado!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar a resposta JSON: " + e.getMessage());
            return;
        }

        boolean exists = favoriteGameRepository
                .findByUserIdAndGameId(userId, favoriteGame.gameId())
                .isPresent();

        if (!exists) {
            System.out.println(userId);
            FavoriteGame newFavoriteGame = new FavoriteGame(favoriteGame.gameId(), userId);
            favoriteGameRepository.save(newFavoriteGame);
        }
    }

    public Optional<FavoriteGame> getFavoriteGameByGameIdAndUserId(Long gameId, UUID userId) {
        return favoriteGameRepository.findByUserIdAndGameId(userId, gameId);
    }

    public List<FavoriteGame> getAllFavoriteGamesByUserId(UUID userId) {
        return favoriteGameRepository.findFavoriteGamesByUserId(userId);
    }

    public void deleteFavoriteGame(int gameId, UUID userId) {
        favoriteGameRepository.deleteByUserIdAndGameId(userId, gameId);
    }
}
