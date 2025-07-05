package com.TP.game_service.services;

import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.Rawg.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RawgApiClient {
    private final RestTemplate restTemplate;

    public GameExtraInfo getGameById(String url) {
        try {
            return restTemplate.getForObject(url, GameExtraInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponseDTO<Game> searchGames(String url) {
        try {
            ResponseEntity<BaseResponseDTO<Game>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<BaseResponseDTO<Game>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponseDTO<PlatformComplete> searchPlatforms(String url) {
        try {
            ResponseEntity<BaseResponseDTO<PlatformComplete>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<BaseResponseDTO<PlatformComplete>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponseDTO<GenreComplete> searchGenres(String url) {
        try {
            ResponseEntity<BaseResponseDTO<GenreComplete>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<BaseResponseDTO<GenreComplete>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponseDTO<StoreComplete> searchStores(String url) {
        try {
            ResponseEntity<BaseResponseDTO<StoreComplete>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<BaseResponseDTO<StoreComplete>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
