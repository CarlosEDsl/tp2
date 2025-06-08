package com.TP.game_service.services;

import com.TP.game_service.adapter.GameAdapter;
import com.TP.game_service.models.DTOs.GameSearchRequest;
import com.TP.game_service.models.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final RawgApiFacade facade;
    private final GameAdapter adapter;

    public GameService(RawgApiFacade facade, GameAdapter adapter) {
        this.facade = facade;
        this.adapter = adapter;
    }

    public List<Game> searchGames(GameSearchRequest request) { //permite a criacao dinamica de um builder
        String response = facade.searchGames(request);

        List<Game> games = adapter.adaptList(response);

        return games;
    }

    public Game getGameById(Long id) {
        String response = facade.getGameById(id);
        return adapter.adapt(response);
    }
}
