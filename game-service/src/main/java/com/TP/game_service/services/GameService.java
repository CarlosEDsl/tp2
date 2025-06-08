package com.TP.game_service.services;

import com.TP.game_service.adapter.GameAdapter;
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

    public List<Game> listGamesByPage(int page) {
        String response = facade.listGamesByPage(page);
        return adapter.adaptList(response);
    }

    public Game getGameById(Long id) {
        String response = facade.getGameById(id);
        return adapter.adapt(response);
    }
}
