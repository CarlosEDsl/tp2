package com.TP.game_service.controllers;

import com.TP.game_service.adapter.GameAdapter;
import com.TP.game_service.models.DTOs.GameSearchRequest;
import com.TP.game_service.models.Game;
import com.TP.game_service.services.GameService;
import com.TP.game_service.services.RawgApiFacade;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {
    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/byId")
    public ResponseEntity<Game> getGameById(@RequestParam("gameId")Long gameId) {
        Game game =  gameService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @GetMapping()
    public ResponseEntity<List<Game>> searchGames(@RequestBody GameSearchRequest request) {
        List<Game> games =  gameService.searchGames(request);
        return ResponseEntity.ok(games);
    }
}
