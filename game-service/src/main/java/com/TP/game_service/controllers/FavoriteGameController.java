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
    public ResponseEntity<String> addFavoriteGame(@RequestBody FavoriteGameRequestDTO favoriteGame) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();
            UUID userId = user.getUserId();
            System.out.println(userId);

            favoriteGameService.saveNewFavoriteGame(favoriteGame, userId);

            return ResponseEntity.ok().build();
        } catch (HttpMessageNotReadableException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar os dados. Verifique o formato da requisição."); // Mensagem personalizada
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno. Tente novamente mais tarde.");
        }
    }

    @GetMapping("/byId")
    public ResponseEntity<FavoriteGame> getFavoriteGame(@RequestParam Long gameId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();
        UUID userId = user.getUserId();

        Optional<FavoriteGame> game = favoriteGameService.getFavoriteGameByGameIdAndUserId(gameId, userId);

        return game.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
