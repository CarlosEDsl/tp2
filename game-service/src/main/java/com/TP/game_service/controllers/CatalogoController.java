package com.TP.game_service.controllers;

import com.TP.game_service.models.*;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
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
    public ResponseEntity<BaseResponseDTO<PlatformAdapted>> searchPlatforms(@RequestBody BaseSearchRequestDTO request) {
        return ResponseEntity.ok(catalogoService.searchPlatforms(request));
    }

    @GetMapping("/rawg/searchGenres")
    public ResponseEntity<BaseResponseDTO<GenreAdapted>> searchGenres(@RequestBody BaseSearchRequestDTO request) {
        return ResponseEntity.ok(catalogoService.searchGenres(request));
    }

    @GetMapping("/rawg/searchStores")
    public ResponseEntity<BaseResponseDTO<StoreAdapted>> searchStores(@RequestBody BaseSearchRequestDTO request) {
        return ResponseEntity.ok(catalogoService.searchStores(request));
    }
}
