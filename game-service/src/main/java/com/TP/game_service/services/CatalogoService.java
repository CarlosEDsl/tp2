package com.TP.game_service.services;

import com.TP.game_service.facade.RawgFacade;
import com.TP.game_service.models.*;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
import com.TP.game_service.repositories.GameRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CatalogoService {
    @Autowired
    private RawgFacade facade;
    @Autowired
    private GameRatingRepository gameRatingRepository;

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

    public BaseResponseDTO<GameAdapted> getTopGames() {
        try {
            List<GameAdapted> response = facade.getTopGames();

            BaseResponseDTO<GameAdapted> responseAdapted = new BaseResponseDTO<GameAdapted>(
                    response.size(),
                    null,
                    null,
                    response
            );

            return responseAdapted;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public void insertNewGameAVG(GameRating gameRating) {
        System.out.println("teste");
        this.gameRatingRepository.save(gameRating);
    }
}
