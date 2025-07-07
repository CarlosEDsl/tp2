package com.TP.game_service.mappers;

import com.TP.game_service.models.*;
import com.TP.game_service.models.Rawg.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameMapper {
    private PlatformMapper platformMapper;
    private GenreMapper genreMapper;
    private StoreMapper storeMapper;

    public GameMapper(PlatformMapper platformMapper, GenreMapper genreMapper, StoreMapper storeMapper) {
        this.platformMapper = platformMapper;
        this.genreMapper = genreMapper;
        this.storeMapper = storeMapper;
    }

    public GameAdapted adapt(Game game) {
        List<PlatformAdapted> plataforms = new ArrayList<>();
        List<GenreAdapted> genres = new ArrayList<>();
        List<StoreAdapted> stores = new ArrayList<>();

        if(game.getPlatforms() != null) {
            for(PlatformInfo platform : game.getPlatforms()) {
                PlatformAdapted platformAdapted = platformMapper.adapt(platform);
                plataforms.add(platformAdapted);
            }
        }

        if(game.getGenres() != null) {
            for(Genre genre : game.getGenres()) {
                GenreAdapted genreAdapted = genreMapper.adapt(genre);
                genres.add(genreAdapted);
            }
        }

        if(game.getStores() != null) {
            for(StoreInfo store : game.getStores()) {
                StoreAdapted storeAdapted = storeMapper.adapt(store);
                stores.add(storeAdapted);
            }
        }

        GameAdapted gameAdapted = new GameAdapted(game.getId(), game.getName(), game.getReleased(), game.getRating(), game.getRatingsCount(), game.getBackgroundImage(), plataforms, genres, stores
        );

        return gameAdapted;
    }

    public GameExtraInfoAdapted adapt(GameExtraInfo request) {
        List<PlatformAdapted> plataforms = new ArrayList<>();
        List<GenreAdapted> genres = new ArrayList<>();
        List<StoreAdapted> stores = new ArrayList<>();

        if(request.getPlatforms() != null) {
            for(PlatformInfo platform : request.getPlatforms()) {
                PlatformAdapted platformAdapted = platformMapper.adapt(platform);
                plataforms.add(platformAdapted);
            }
        }

        if(request.getGenres() != null) {
            for(Genre genre : request.getGenres()) {
                GenreAdapted genreAdapted = genreMapper.adapt(genre);
                genres.add(genreAdapted);
            }
        }

        if(request.getStores() != null) {
            for(StoreInfo store : request.getStores()) {
                StoreAdapted storeAdapted = storeMapper.adapt(store);
                stores.add(storeAdapted);
            }
        }

        GameExtraInfoAdapted gameExtraInfoAdapted = new GameExtraInfoAdapted(request.getId(), request.getName(), request.getDescription(), request.getReleased(), request.getRating(), request.getRatingsCount(), request.getAchievementsCount(), request.getWebsite(), request.getBackgroundImage(), plataforms, genres, stores);

        return gameExtraInfoAdapted;
    }
}
