package com.TP.game_service.builders;

import com.TP.game_service.interfaces.IRawgBuilder;
import com.TP.game_service.interfaces.IRawgGameBuilder;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RawgDirector {
    private IRawgBuilder builder;

    @Autowired
    private IRawgGameBuilder gameBuilder;

    @Value("40fafc5be329409a87eccd2be4e515df")
    private String apiKey;

    public void setBuilder(IRawgBuilder builder) {
        System.out.println("Setando builder: " + builder.getClass().getName());
        this.builder = builder;
    }

    public String getById(Long id) {
        builder.setApiKey(apiKey);
        return builder.getById(id);
    }

    public String searchGames(GameSearchRequestDTO request) {
        builder.setBaseUrl();
        builder.setApiKey(apiKey);
        builder.page(request.getPage());
        builder.pageSize(request.getPageSize());

        if (request.getSearch() != null) {
            gameBuilder.search(request.getSearch());

            if(request.getSearchExact() != null) {
                gameBuilder.searchExact(request.getSearchExact());
            }
            if(request.getSearchPrecise() != null) {
                gameBuilder.searchPrecise(request.getSearchPrecise());
            }
        }
        if (request.getGenre() != null) {
            gameBuilder.genre(request.getGenre());
        }
        if (request.getPlatform() != null) {
            gameBuilder.platform(request.getPlatform());
        }
        if (request.getOrdering() != null) {
            builder.ordering(request.getOrdering());
        }
        if (request.getStores() != 0) {
            gameBuilder.stores(request.getStores());
        }

        String url = builder.build();
        return url;
    }

    public String baseSearch(BaseSearchRequestDTO request) {
        builder.setBaseUrl();
        builder.setApiKey(apiKey);
        builder.page(request.getPage());
        builder.pageSize(request.getPageSize());

        if (request.getOrdering() != null) {
            builder.ordering(request.getOrdering());
        }

        String url = builder.build();
        return url;
    }

    public String getTopGames() {
        builder.setBaseUrl();
        builder.setApiKey(apiKey);
        builder.ordering("rating");
        gameBuilder.metacritic("80,100");
        gameBuilder.excludeAdditions(true);

        String url = builder.build();
        return url;
    }
}
