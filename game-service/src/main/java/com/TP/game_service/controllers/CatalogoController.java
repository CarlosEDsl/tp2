package com.TP.game_service.controllers;

import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.DTOs.GenreSearchRequest;
import com.TP.game_service.models.DTOs.PlatformSearchRequest;
import com.TP.game_service.models.GameAdapted;
import com.TP.game_service.models.GameExtraInfoAdapted;
import com.TP.game_service.models.GenreAdapted;
import com.TP.game_service.models.PlatformAdapted;
import com.TP.game_service.services.CatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping("/rawg/gameById")
    public ResponseEntity<GameExtraInfoAdapted> getGameById(@RequestParam("gameId")Long gameId) {
        return ResponseEntity.ok(catalogoService.getGameById(gameId));
    }

    @GetMapping("/rawg/searchGames")
    public ResponseEntity<BaseResponseDTO<GameAdapted>> searchGames(@RequestBody GameSearchRequestDTO request) {
        return ResponseEntity.ok(catalogoService.searchGames(request));
    }

    @GetMapping("/rawg/searchPlatforms")
    public ResponseEntity<BaseResponseDTO<PlatformAdapted>> searchPlatforms(@RequestBody PlatformSearchRequest request) {
        return ResponseEntity.ok(catalogoService.searchPlatforms(request));
    }

    @GetMapping("/rawg/searchGenres")
    public ResponseEntity<BaseResponseDTO<GenreAdapted>> searchGenres(@RequestBody GenreSearchRequest request) {
        return ResponseEntity.ok(catalogoService.searchGenres(request));
    }
}
