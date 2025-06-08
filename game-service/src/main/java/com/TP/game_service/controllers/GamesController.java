package com.TP.game_service.controllers;

import com.TP.game_service.adapter.GameAdapter;
import com.TP.game_service.models.Game;
import com.TP.game_service.services.GameService;
import com.TP.game_service.services.RawgApiFacade;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {
    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/byPage")
    public ResponseEntity<List<Game>> listGamesByPage(@RequestParam("page") int page) {
        List<Game> games =  gameService.listGamesByPage(page);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/byId")
    public ResponseEntity<Game> getGameById(@RequestParam("id") Long id) {
        Game game =  gameService.getGameById(id);
        return ResponseEntity.ok(game);
    }
}
