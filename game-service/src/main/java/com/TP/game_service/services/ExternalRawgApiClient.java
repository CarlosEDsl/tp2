package com.TP.game_service.services;

import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.Rawg.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalRawgApiClient {
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

    public BaseResponseDTO<PlatformInfo> searchPlatforms(String url) {
        try {
            ResponseEntity<BaseResponseDTO<PlatformInfo>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<BaseResponseDTO<PlatformInfo>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponseDTO<Genre> searchGenres(String url) {
        try {
            ResponseEntity<BaseResponseDTO<Genre>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<BaseResponseDTO<Genre>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
