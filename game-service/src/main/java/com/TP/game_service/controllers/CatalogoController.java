package com.TP.game_service.controllers;

import com.TP.game_service.models.DTOs.GameSearchRequest;
import com.TP.game_service.models.DTOs.GenreSearchRequest;
import com.TP.game_service.models.DTOs.PlatformSearchRequest;
import com.TP.game_service.models.Game;
import com.TP.game_service.models.Genre;
import com.TP.game_service.models.Plataform;
import com.TP.game_service.services.CatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping("/gameById")
    public ResponseEntity<Game> getGameById(@RequestParam("gameId")Long gameId) {
        Game game =  catalogoService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/searchGames")
    public ResponseEntity<List<Game>> searchGames(@RequestBody GameSearchRequest request) {
        List<Game> games =  catalogoService.searchGames(request);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/searchPlatforms")
    public ResponseEntity<List<Plataform>> searchPlatforms(@RequestBody PlatformSearchRequest request) {
        List<Plataform> plataforms =  catalogoService.searchPlatforms(request);
        return ResponseEntity.ok(plataforms);
    }

    @GetMapping("/searchGenres")
    public ResponseEntity<List<Genre>> searchGenres(@RequestBody GenreSearchRequest request) {
        List<Genre> genres = catalogoService.searchGenres(request);
        return ResponseEntity.ok(genres);
    }
}
