package com.TP.game_service.adapter;

import com.TP.game_service.models.Genre;
import com.TP.game_service.models.Plataform;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreAdapter {

    public List<Genre> adaptList(String jsonArrayResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonArrayResponse);
            JsonNode results = root.get("results").get("genres");
            List<Genre> genreList = new ArrayList<>();

            if (results != null && results.isArray()) {
                for (JsonNode node : results) {
                    Long id = node.get("id").asLong();
                    String name = node.get("name").asText();

                    Genre genre = new Genre(id, name);

                    genreList.add(genre);
                }
            }

            return genreList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao adaptar lista de generos", e);
        }
    }
}
