package com.TP.game_service.services;

import com.TP.game_service.adapter.GenreAdapter;
import com.TP.game_service.adapter.PlatformAdapter;
import com.TP.game_service.facade.RawgApiFacade;
import com.TP.game_service.adapter.GameAdapter;
import com.TP.game_service.models.DTOs.GameSearchRequest;
import com.TP.game_service.models.DTOs.GenreSearchRequest;
import com.TP.game_service.models.DTOs.PlatformSearchRequest;
import com.TP.game_service.models.Game;
import com.TP.game_service.models.Genre;
import com.TP.game_service.models.Plataform;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {
    private final RawgApiFacade facade;
    private final GameAdapter gameAdapter;
    private final GenreAdapter genreAdapter;
    private final PlatformAdapter platformAdapter;

    public CatalogoService(RawgApiFacade facade, GameAdapter gameAdapter, GenreAdapter genreAdapter, PlatformAdapter platformAdapter) {
        this.facade = facade;
        this.gameAdapter = gameAdapter;
        this.genreAdapter = genreAdapter;
        this.platformAdapter = platformAdapter;
    }

    public List<Game> searchGames(GameSearchRequest request) {
        String response = facade.searchGames(request);

        List<Game> games = gameAdapter.adaptList(response);

        return games;
    }

    public Game getGameById(Long id) {
        String response = facade.getGameById(id);
        return gameAdapter.adapt(response);
    }

    public List<Genre> searchGenres(GenreSearchRequest request) {
        String response = facade.searchGenres(request);

        List<Genre> genres = genreAdapter.adaptList(response);

        return genres;
    }

    public List<Plataform> searchPlatforms(PlatformSearchRequest request) {
        String response = facade.searchPlatforms(request);

        List<Plataform> platforms = platformAdapter.adaptList(response);

        return platforms;
    }
}
