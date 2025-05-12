package com.TP.game_service.controllers;

import com.TP.game_service.models.DTOs.FavoriteGameRequestDTO;
import com.TP.game_service.security.MinimalUserDetails;
import com.TP.game_service.services.FavoriteGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/favoriteGame")
public class FavoriteGameController {
    private final FavoriteGameService favoriteGameService;

    public FavoriteGameController(FavoriteGameService favoriteGameService) {
        this.favoriteGameService = favoriteGameService;
    }

    @PostMapping()
    public ResponseEntity<Void> addFavoriteGame(@RequestBody FavoriteGameRequestDTO favoriteGame) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();
        UUID userId = user.getUserId();

        favoriteGameService.saveNewFavoriteGame(favoriteGame, userId);

        return ResponseEntity.ok().build();
    }
}
