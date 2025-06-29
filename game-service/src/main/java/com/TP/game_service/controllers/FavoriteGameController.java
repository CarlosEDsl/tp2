package com.TP.game_service.controllers;

import com.TP.game_service.models.DTOs.FavoriteGameRequestDTO;
import com.TP.game_service.models.FavoriteGame;
import com.TP.game_service.security.MinimalUserDetails;
import com.TP.game_service.services.FavoriteGameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/favoriteGame")
public class FavoriteGameController {
    private final FavoriteGameService favoriteGameService;

    public FavoriteGameController(FavoriteGameService favoriteGameService) {
        this.favoriteGameService = favoriteGameService;
    }

    @PostMapping()
    public ResponseEntity<String> addFavoriteGame(@RequestParam("gameId") Long gameId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();

            UUID userId = user.getUserId();
            if(gameId == null || gameId < 0) {
                return ResponseEntity.badRequest().body("ID do jogo inválido");
            }

            favoriteGameService.saveNewFavoriteGame(gameId, userId);

            return ResponseEntity.ok().build();
        } catch (HttpMessageNotReadableException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar os dados. Verifique o formato da requisição."); // Mensagem personalizada
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno. Tente novamente mais tarde.");
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<FavoriteGame> getFavoriteGame(@RequestParam("gameId") Long gameId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();
        UUID userId = user.getUserId();

        Optional<FavoriteGame> game = favoriteGameService.getFavoriteGameByGameIdAndUserId(gameId, userId);

        return game.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FavoriteGame>> getAllFavoriteGames() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();
        UUID userId = user.getUserId();

        List<FavoriteGame> favoriteGames = favoriteGameService.getAllFavoriteGamesByUserId(userId);
        if(favoriteGames.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(favoriteGames, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteFavoriteGame(@RequestParam("gameId") Long gameId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();
            UUID userId = user.getUserId();
            if(gameId == null || gameId < 0) {
                return ResponseEntity.badRequest().body("ID do jogo inválido");
            }

            Boolean result = favoriteGameService.deleteFavoriteGame(gameId, userId);
            if(!result){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
