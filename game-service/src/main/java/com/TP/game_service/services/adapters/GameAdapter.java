package com.TP.game_service.services.adapters;

import com.TP.game_service.models.*;
import com.TP.game_service.models.Rawg.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameAdapter {
    private PlatformAdapter platformAdapter;
    private GenreAdapter genreAdapter;
    private StoreAdapter storeAdapter;

    public GameAdapter() {
        this.platformAdapter = new PlatformAdapter();
        this.genreAdapter = new GenreAdapter();
        this.storeAdapter = new StoreAdapter();
    }

    public GameAdapted adapt(Game game) {
        List<PlatformAdapted> plataforms = new ArrayList<>();
        List<GenreAdapted> genres = new ArrayList<>();
        List<StoreAdapted> stores = new ArrayList<>();

        if(game.getPlatforms() != null) {
            for(PlatformInfo platform : game.getPlatforms()) {
                PlatformAdapted platformAdapted = platformAdapter.adapt(platform);
                plataforms.add(platformAdapted);
            }
        }

        if(game.getGenres() != null) {
            for(Genre genre : game.getGenres()) {
                GenreAdapted genreAdapted = genreAdapter.adapt(genre);
                genres.add(genreAdapted);
            }
        }

        if(game.getStores() != null) {
            for(StoreInfo store : game.getStores()) {
                StoreAdapted storeAdapted = storeAdapter.adapt(store);
                stores.add(storeAdapted);
            }
        }

        GameAdapted gameAdapted = new GameAdapted(game.getId(), game.getName(), game.getReleased(), game.getRating(), game.getRatingsCount(), game.getBackgroundImage(), plataforms, genres, stores
        );

        return gameAdapted;
    }

    public GameExtraInfoAdapted adapt(GameExtraInfo game) {
        List<PlatformAdapted> plataforms = new ArrayList<>();
        List<GenreAdapted> genres = new ArrayList<>();

        if(game.getPlatforms() != null) {
            for(PlatformInfo platform : game.getPlatforms()) {
                PlatformAdapted platformAdapted = platformAdapter.adapt(platform);
                plataforms.add(platformAdapted);
            }
        }

        if(game.getGenres() != null) {
            for(Genre genre : game.getGenres()) {
                GenreAdapted genreAdapted = genreAdapter.adapt(genre);
                genres.add(genreAdapted);
            }
        }

        GameExtraInfoAdapted gameExtraInfoAdapted = new GameExtraInfoAdapted(game.getId(), game.getName(), game.getDescription(), game.getReleased(), game.getRating(), game.getRatingsCount(), game.getAchievementsCount(), game.getWebsite(), game.getBackgroundImage(), plataforms, genres);

        return gameExtraInfoAdapted;
    }
}
