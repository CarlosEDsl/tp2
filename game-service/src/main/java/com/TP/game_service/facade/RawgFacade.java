package com.TP.game_service.facade;

import com.TP.game_service.builders.*;
import com.TP.game_service.mappers.GameMapper;
import com.TP.game_service.mappers.GenreMapper;
import com.TP.game_service.mappers.PlatformMapper;
import com.TP.game_service.mappers.StoreMapper;
import com.TP.game_service.models.*;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.services.RawgApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RawgFacade {
    private final RawgDirector director;
    private final GamesUrlBuilder gamesUrlBuilder;
    private final GenresUrlBuilder genresUrlBuilder;
    private final PlatformsUrlBuilder platformsUrlBuilder;
    private final StoresUrlBuilder storesUrlBuilder;
    private final GameMapper gameMapper;
    private final StoreMapper storeMapper;
    private final PlatformMapper platformMapper;
    private final GenreMapper genreMapper;
    private final RawgApiClient apiClient;

    public RawgFacade(RawgDirector director, GamesUrlBuilder gamesUrlBuilder, GenresUrlBuilder genresUrlBuilder,
                      PlatformsUrlBuilder platformsUrlBuilder, StoresUrlBuilder storesUrlBuilder, GameMapper gameMapper,
                      StoreMapper storeMapper, PlatformMapper platformMapper, GenreMapper genreMapper, RawgApiClient apiClient) {
        this.director = director;
        this.gamesUrlBuilder = gamesUrlBuilder;
        this.genresUrlBuilder = genresUrlBuilder;
        this.platformsUrlBuilder = platformsUrlBuilder;
        this.storesUrlBuilder = storesUrlBuilder;
        this.gameMapper = gameMapper;
        this.storeMapper = storeMapper;
        this.platformMapper = platformMapper;
        this.genreMapper = genreMapper;
        this.apiClient = apiClient;
    }

    public GameExtraInfoAdapted getGameById(Long gameId) {
        director.setBuilder(gamesUrlBuilder);
        String url = director.getById(gameId);
        System.out.println("url: " + url);

        GameExtraInfo game = apiClient.getGameById(url);

        return gameMapper.adapt(game);
    }

    public BaseResponseDTO<GameAdapted> searchGames(GameSearchRequestDTO request) {
        director.setBuilder(gamesUrlBuilder);
        String url = director.searchGames(request);

        BaseResponseDTO<Game> response = apiClient.searchGames(url);
        List<GameAdapted> gameAdapteds = new ArrayList<>();

        for (Game game : response.getResults()) {
            GameAdapted gameAdapted = gameMapper.adapt(game);
            gameAdapteds.add(gameAdapted);
        }

        BaseResponseDTO<GameAdapted> responseAdapted = new BaseResponseDTO<GameAdapted>(
                response.getCount(),
                response.getNext(),
                response.getPrevious(),
                gameAdapteds
        );

        return responseAdapted;
    }

    public BaseResponseDTO<PlatformAdapted> searchPlatforms(BaseSearchRequestDTO request) {
        director.setBuilder(platformsUrlBuilder);
        String url = director.baseSearch(request);

        BaseResponseDTO<PlatformComplete> response = apiClient.searchPlatforms(url);
        List<PlatformAdapted> platformAdapteds = new ArrayList<>();

        for (PlatformComplete platform : response.getResults()) {
            PlatformAdapted platformAdapted = platformMapper.adapt(platform);
            platformAdapteds.add(platformAdapted);
        }

        BaseResponseDTO<PlatformAdapted> responseAdapted = new BaseResponseDTO<PlatformAdapted>(
                response.getCount(),
                response.getNext(),
                response.getPrevious(),
                platformAdapteds
        );

        return responseAdapted;
    }

    public BaseResponseDTO<GenreAdapted> searchGenres(BaseSearchRequestDTO request) {
        director.setBuilder(genresUrlBuilder);
        String url = director.baseSearch(request);

        BaseResponseDTO<GenreComplete> response = apiClient.searchGenres(url);
        List<GenreAdapted> genreAdapteds = new ArrayList<>();

        for (GenreComplete genre : response.getResults()) {
            GenreAdapted genreAdapted = genreMapper.adapt(genre);
            genreAdapteds.add(genreAdapted);
        }

        BaseResponseDTO<GenreAdapted> responseAdapted = new BaseResponseDTO<GenreAdapted>(
                response.getCount(),
                response.getNext(),
                response.getPrevious(),
                genreAdapteds
        );

        return responseAdapted;
    }

    public BaseResponseDTO<StoreAdapted> searchStores(BaseSearchRequestDTO request) {
        director.setBuilder(storesUrlBuilder);
        String url = director.baseSearch(request);

        BaseResponseDTO<StoreComplete> response = apiClient.searchStores(url);
        List<StoreAdapted> storeAdapteds = new ArrayList<>();

        for (StoreComplete store : response.getResults()) {
            StoreAdapted storeAdapted = storeMapper.adapt(store);
            storeAdapteds.add(storeAdapted);
        }

        BaseResponseDTO<StoreAdapted> responseAdapted = new BaseResponseDTO<StoreAdapted>(
                response.getCount(),
                response.getNext(),
                response.getPrevious(),
                storeAdapteds
        );

        return responseAdapted;
    }

    public List<GameAdapted> getTopGames() {
        director.setBuilder(gamesUrlBuilder);
        String url = director.getTopGames();

        BaseResponseDTO<Game> response = apiClient.searchGames(url);
        List<GameAdapted> gameAdapteds = new ArrayList<>();

        for (Game game : response.getResults()) {
            if(game.getRatingsCount() > 500) {
                GameAdapted gameAdapted = gameMapper.adapt(game);

                if(gameAdapteds.size() < 10) {
                    gameAdapteds.add(gameAdapted);
                }
            }
        }

        return gameAdapteds;
    }
}
