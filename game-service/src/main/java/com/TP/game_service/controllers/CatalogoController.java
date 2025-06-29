package com.TP.game_service.controllers;

import com.TP.game_service.models.*;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
import com.TP.game_service.services.CatalogoService;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getGameById(@RequestParam("gameId")Long gameId) {
        try {
            if (gameId == null || gameId <= 0) {
                return ResponseEntity.badRequest().body("ID do jogo inválido.");
            }

            GameExtraInfoAdapted game = catalogoService.getGameById(gameId);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar jogo por ID: " + e.getMessage());
        }
    }

    @GetMapping("/rawg/searchGames")
    public ResponseEntity<?> searchGames(@RequestBody GameSearchRequestDTO request) {
        try {
            BaseResponseDTO<GameAdapted> response = catalogoService.searchGames(request);
            if(response.getResults().size() == 0) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar jogos: " + e.getMessage());
        }
    }

    @GetMapping("/rawg/searchPlatforms")
    public ResponseEntity<?> searchPlatforms(@RequestBody BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<PlatformAdapted> response = catalogoService.searchPlatforms(request);
            if(response.getResults().size() == 0) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar plataformas: " + e.getMessage());
        }
    }

    @GetMapping("/rawg/searchGenres")
    public ResponseEntity<?> searchGenres(@RequestBody BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<GenreAdapted> response = catalogoService.searchGenres(request);
            if(response.getResults().size() == 0) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar gêneros: " + e.getMessage());
        }
    }

    @GetMapping("/rawg/searchStores")
    public ResponseEntity<?> searchStores(@RequestBody BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<StoreAdapted> response = catalogoService.searchStores(request);
            if(response.getResults().size() == 0) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar lojas: " + e.getMessage());
        }
    }
}
