package com.TP.game_service.adapter;

import com.TP.game_service.models.Game;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameAdapter {

    public Game adapt(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response);

            Long id = json.get("id").asLong();
            String name = json.get("name").asText();
            String released = json.get("released").asText();
            float rating = (float) json.get("rating").asDouble();
            String backgroundImage = json.get("background_image").asText();

            return new Game(id, name, released, rating, backgroundImage);
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
                for (JsonNode node : results) {
                    Long id = node.get("id").asLong();
                    String name = node.get("name").asText();
                    String released = node.hasNonNull("released") ? node.get("released").asText() : null;
                    float rating = node.hasNonNull("rating") ? (float) node.get("rating").asDouble() : 0.0f;
                    String backgroundImage = node.hasNonNull("background_image") ? node.get("background_image").asText() : null;

                    Game game = new Game(id, name, released, rating, backgroundImage);
                    games.add(game);
                }
            }

            return games;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao adaptar lista de jogos", e);
        }
    }
}
