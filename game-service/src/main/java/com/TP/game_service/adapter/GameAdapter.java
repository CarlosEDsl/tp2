package com.TP.game_service.adapter;

import com.TP.game_service.models.Game;
import com.TP.game_service.models.Genre;
import com.TP.game_service.models.Plataform;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameAdapter {
    private PlatformAdapter platformAdapter;
    private GenreAdapter genreAdapter;

    public GameAdapter(PlatformAdapter platformAdapter, GenreAdapter genreAdapter) {
        this.platformAdapter = platformAdapter;
        this.genreAdapter = genreAdapter;
    }

    public Game adapt(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response);

            Long id = json.get("id").asLong();
            String name = json.get("name").asText();
            String released = json.get("released").asText();
            float rating = (float) json.get("rating").asDouble();
            int ratings_count = json.get("ratings_count").asInt();
            String backgroundImage = json.get("background_image").asText();

            List<Plataform> platforms = new ArrayList<>();
            for (JsonNode node : json.get("platforms")) {
                JsonNode platformNode = node.get("platform");
                if (platformNode != null) {
                    Plataform platform = platformAdapter.adapt(platformNode);
                    platforms.add(platform);
                }
            }

            List<Genre> genres = new ArrayList<>();
            for(JsonNode node : json.get("genres")){
                if(node != null){
                    Genre genre = genreAdapter.adapt(node);
                    genres.add(genre);
                }
            }

            return new Game(id, name, released, rating, ratings_count, backgroundImage, platforms, genres);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adaptar Game: " + e.getMessage(), e);
        }
    }

    public List<Game> adaptList(String jsonArrayResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonArrayResponse); // raiz é um objeto
            JsonNode results = root.get("results"); // "results" é o array de jogos
            List<Game> games = new ArrayList<>();

            if (results != null && results.isArray()) {
                for (JsonNode json : results) {
                    Long id = json.get("id").asLong();
                    String name = json.get("name").asText();
                    String released = json.get("released").asText();
                    float rating = (float) json.get("rating").asDouble();
                    int ratings_count = json.get("ratings_count").asInt();
                    String backgroundImage = json.get("background_image").asText();

                    List<Plataform> platforms = new ArrayList<>();
                    for (JsonNode node : json.get("platforms")) {
                        JsonNode platformNode = node.get("platform"); // CORREÇÃO AQUI
                        if (platformNode != null) {
                            Plataform platform = platformAdapter.adapt(platformNode);
                            platforms.add(platform);
                        }
                    }

                    List<Genre> genres = new ArrayList<>();
                    for(JsonNode node : json.get("genres")){
                        if(node != null){
                            Genre genre = genreAdapter.adapt(node);
                            genres.add(genre);
                        }
                    }

                    Game game = new Game(id, name, released, rating, ratings_count, backgroundImage, platforms, genres);
                    games.add(game);
                }
            }

            return games;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao adaptar lista de jogos", e);
        }
    }
}
