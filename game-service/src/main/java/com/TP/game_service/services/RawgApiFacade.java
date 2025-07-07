package com.TP.game_service.services;

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

    public String getGameById(Long id) {
        String url = new RawgUrlBuilder(rawgApiKey)
                .getById(id)
                .build();

        return sendGetRequest(url);
    }

    public String getGames(int page, String genre, String query) {
        String url = new RawgUrlBuilder(rawgApiKey)
                .page(page)
                .genre(genre)
                .search(query)
                .build();

        return sendGetRequest(url);
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
