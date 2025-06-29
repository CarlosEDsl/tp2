package com.TP.game_service.services.facade;

import com.TP.game_service.models.GameExtraInfoAdapted;
import com.TP.game_service.models.Rawg.GameExtraInfo;
import com.TP.game_service.services.adapters.GameAdapter;
import com.TP.game_service.services.adapters.GenreAdapter;
import com.TP.game_service.services.adapters.PlatformAdapter;
import com.TP.game_service.models.GameAdapted;
import com.TP.game_service.models.GenreAdapted;
import com.TP.game_service.models.PlatformAdapted;
import com.TP.game_service.models.Rawg.Game;
import com.TP.game_service.models.Rawg.Genre;
import com.TP.game_service.models.Rawg.PlatformInfo;
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

    public List<PlatformAdapted> searchPlatforms(List<PlatformInfo> platforms) {
        List<PlatformAdapted> platformAdaptedList = new ArrayList<>();
        for(PlatformInfo platform : platforms) {
            PlatformAdapted platformAdapted = platformAdapter.adapt(platform);
            platformAdaptedList.add(platformAdapted);
        }

        return platformAdaptedList;
    }

    public List<GenreAdapted> searchGenres(List<Genre> genres) {
        List<GenreAdapted> genreAdaptedList = new ArrayList<>();
        for(Genre genre : genres) {
            GenreAdapted genreAdapted = genreAdapter.adapt(genre);
            genreAdaptedList.add(genreAdapted);
        }

        return genreAdaptedList;
    }
}
