package com.TP.game_service.services.facade;

import com.TP.game_service.builders.GenresUrlBuilder;
import com.TP.game_service.builders.PlatformsUrlBuilder;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.builders.GamesUrlBuilder;
import com.TP.game_service.models.DTOs.GenreSearchRequest;
import com.TP.game_service.models.DTOs.PlatformSearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiUrlsFacade {
    @Value("${rawg.api.key}")
    private String rawgApiKey;

    public String getGameById(Long gameId) {
        GamesUrlBuilder builder = new GamesUrlBuilder(rawgApiKey)
                .getById(gameId);

        String url = builder.build();
        return url;
    }

    public String searchGames(GameSearchRequestDTO request) {
        GamesUrlBuilder builder = new GamesUrlBuilder(rawgApiKey)
                .page(request.getPage())
                .pageSize(request.getPageSize());

        if (request.getSearch() != null) {
            builder.search(request.getSearch());

            if(request.getSearchExact() != null) {
                builder.searchExact(request.getSearchExact());
            }
            if(request.getSearchPrecise() != null) {
                builder.searchPrecise(request.getSearchPrecise());
            }
        }
        if (request.getGenre() != null) {
            builder.genre(request.getGenre());
        }
        if (request.getPlatform() != null) {
            builder.platform(request.getPlatform());
        }
        if (request.getOrdering() != null) {
            builder.ordering(request.getOrdering());
        }
        if (request.getStores() != 0) {
            builder.stores(request.getStores());
        }

        String url = builder.build();
        return url;
    }

    public String searchPlatforms(PlatformSearchRequest request) {
        PlatformsUrlBuilder builder = new PlatformsUrlBuilder(rawgApiKey)
                .page(request.getPage())
                .pageSize(request.getPageSize());

        if (request.getOrdering() != null) {
            builder.ordering(request.getOrdering());
        }

        String url = builder.build();
        return url;
    }

    public String searchGenres(GenreSearchRequest request) {
        GenresUrlBuilder builder = new GenresUrlBuilder(rawgApiKey)
                .page(request.getPage())
                .pageSize(request.getPageSize());

        if (request.getOrdering() != null) {
            builder.ordering(request.getOrdering());
        }

        String url = builder.build();
        return url;
    }
}
