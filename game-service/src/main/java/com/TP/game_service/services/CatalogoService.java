package com.TP.game_service.services;

import com.TP.game_service.facade.RawgFacade;
import com.TP.game_service.models.*;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogoService {
    @Autowired
    private RawgFacade facade;

    public GameExtraInfoAdapted getGameById(Long gameId) {
        try {
            GameExtraInfoAdapted response = facade.getGameById(gameId);
            return response;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public BaseResponseDTO<GameAdapted> searchGames(GameSearchRequestDTO request) {
        try {
            BaseResponseDTO<GameAdapted> response = facade.searchGames(request);
            return response;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public BaseResponseDTO<PlatformAdapted> searchPlatforms(BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<PlatformAdapted> response = facade.searchPlatforms(request);
            return response;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public BaseResponseDTO<GenreAdapted> searchGenres(BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<GenreAdapted> response = facade.searchGenres(request);
            return response;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public BaseResponseDTO<StoreAdapted> searchStores(BaseSearchRequestDTO request) {
        try {
            BaseResponseDTO<StoreAdapted> response = facade.searchStores(request);
            return response;
        }
        catch (Exception e) {
            throw e;
        }
    }
}
