package com.TP.game_service.services;

import com.TP.game_service.models.*;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.services.facade.ExternalApiUrlsFacade;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
import com.TP.game_service.services.facade.RawgAdaptedModelsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoService {
    private final ExternalApiUrlsFacade externalApiUrlsFacade;
    private final ExternalRawgApiClient externalRawgApiClient;
    private final RawgAdaptedModelsFacade rawgAdaptedModelsFacade;

    public GameExtraInfoAdapted getGameById(Long gameId) {
        GameExtraInfo game = externalRawgApiClient.getGameById(externalApiUrlsFacade.getGameById(gameId));
        return rawgAdaptedModelsFacade.getGameById(game);
    }

    public BaseResponseDTO<GameAdapted> searchGames(GameSearchRequestDTO request) {
        try {
            BaseResponseDTO<Game> response = externalRawgApiClient.searchGames(externalApiUrlsFacade.searchGames(request));

            List<GameAdapted> games = new ArrayList<>();
            if(response.getResults() != null && response.getResults().size() > 0) {
                games = rawgAdaptedModelsFacade.searchGames(response.getResults());
            }

            BaseResponseDTO<GameAdapted> responseAdapted = new BaseResponseDTO<GameAdapted>(
                    response.getCount(),
                    response.getNext(),
                    response.getPrevious(),
                    games
            );
            return responseAdapted;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseResponseDTO<PlatformAdapted> searchPlatforms(BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<PlatformComplete> response = externalRawgApiClient.searchPlatforms(externalApiUrlsFacade.searchPlatforms(request));

            List<PlatformAdapted> platforms =  new ArrayList<>();
            if(response.getResults() != null && response.getResults().size() > 0) {
                platforms = rawgAdaptedModelsFacade.searchPlatforms(response.getResults());
            }

            BaseResponseDTO<PlatformAdapted> responseAdapted = new BaseResponseDTO<PlatformAdapted>(
                    response.getCount(),
                    response.getNext(),
                    response.getPrevious(),
                    platforms
            );
            return responseAdapted;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseResponseDTO<GenreAdapted> searchGenres(BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<GenreComplete> response = externalRawgApiClient.searchGenres(externalApiUrlsFacade.searchGenres(request));

            List<GenreAdapted> genres = new ArrayList<>();
            if(response.getResults() != null && response.getResults().size() > 0) {
                genres = rawgAdaptedModelsFacade.searchGenres(response.getResults());
            }

            BaseResponseDTO<GenreAdapted> responseAdapted = new BaseResponseDTO<GenreAdapted>(
                    response.getCount(),
                    response.getNext(),
                    response.getPrevious(),
                    genres
            );
            return responseAdapted;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseResponseDTO<StoreAdapted> searchStores(BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<StoreComplete> response = externalRawgApiClient.searchStores(externalApiUrlsFacade.searchStores(request));

            List<StoreAdapted> stores = new ArrayList<>();
            if(response.getResults() != null && response.getResults().size() > 0) {
                stores = rawgAdaptedModelsFacade.searchStores(response.getResults());
            }

            BaseResponseDTO<StoreAdapted> responseAdapted = new BaseResponseDTO<StoreAdapted>(
                    response.getCount(),
                    response.getNext(),
                    response.getPrevious(),
                    stores
            );
            return responseAdapted;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
