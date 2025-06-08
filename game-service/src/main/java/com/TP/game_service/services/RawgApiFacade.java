package com.TP.game_service.services;

import com.TP.game_service.models.DTOs.GameSearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class RawgApiFacade {

    @Value("${rawg.api.key}")
    private String rawgApiKey;

    public String searchGames(GameSearchRequest request) { //permite a criacao dinamica de um builder
        RawgUrlBuilder builder = new RawgUrlBuilder(rawgApiKey)
                .page(request.getPage())
                .pageSize(request.getPageSize());

        if (request.getSearch() != null) {
            builder.search(request.getSearch());
        }
        if (request.getGenre() != null) {
            builder.genre(request.getGenre());
        }
        if (request.getPlatform() != null) {
            builder.platform(request.getPlatform());
        }
        if (request.getOrdering() != null) {
            builder.ordering(request.getOrdering());
        }

        String url = builder.build();
        return sendGetRequest(url);
    }

    public String getGameById(Long gameId) {
        RawgUrlBuilder builder = new RawgUrlBuilder(rawgApiKey)
                .getById(gameId);
        return sendGetRequest(builder.build());
    }

    private String sendGetRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            return response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Erro na requisição";
        }
    }
}
