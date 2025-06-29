package com.TP.game_service.services.facade;

import com.TP.game_service.models.*;
import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.services.adapters.GameAdapter;
import com.TP.game_service.services.adapters.GenreAdapter;
import com.TP.game_service.services.adapters.PlatformAdapter;
import com.TP.game_service.services.adapters.StoreAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RawgAdaptedModelsFacade {
    private final GameAdapter gameAdapter;
    private final PlatformAdapter platformAdapter;
    private final GenreAdapter genreAdapter;
    private final StoreAdapter storeAdapter;

    public GameExtraInfoAdapted getGameById(GameExtraInfo game) {
        return gameAdapter.adapt(game);
    }

    public List<GameAdapted> searchGames(List<Game> games) {
        List<GameAdapted> gameAdaptedList = new ArrayList<>();
        for(Game game : games) {
            GameAdapted gameAdapted = gameAdapter.adapt(game);
            gameAdaptedList.add(gameAdapted);
        }

        return gameAdaptedList;
    }

    public List<PlatformAdapted> searchPlatforms(List<PlatformComplete> platforms) {
        List<PlatformAdapted> platformAdaptedList = new ArrayList<>();
        for(PlatformComplete platform : platforms) {
            PlatformAdapted platformAdapted = platformAdapter.adapt(platform);
            platformAdaptedList.add(platformAdapted);
        }

        return platformAdaptedList;
    }

    public List<GenreAdapted> searchGenres(List<GenreComplete> genres) {
        List<GenreAdapted> genreAdaptedList = new ArrayList<>();
        for(GenreComplete genre : genres) {
            GenreAdapted genreAdapted = genreAdapter.adapt(genre);
            genreAdaptedList.add(genreAdapted);
        }

        return genreAdaptedList;
    }

    public List<StoreAdapted> searchStores(List<StoreComplete> stores) {
        List<StoreAdapted> storeAdaptedList = new ArrayList<>();
        for(StoreComplete store : stores) {
            StoreAdapted storeAdapted = storeAdapter.adapt(store);
            storeAdaptedList.add(storeAdapted);
        }

        return storeAdaptedList;
    }
}
