package com.TP.game_service.adapter;

import com.TP.game_service.models.Game;
import com.TP.game_service.models.Plataform;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlatformAdapter {

    public List<Plataform> adaptList(String jsonArrayResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonArrayResponse);
            JsonNode results = root.get("results").get("platforms");
            List<Plataform> plataformList = new ArrayList<>();

            if (results != null && results.isArray()) {
                for (JsonNode node : results) {
                    Long id = node.get("id").asLong();
                    String name = node.get("name").asText();

                    Plataform plataform = new Plataform(id, name);

                    plataformList.add(plataform);
                }
            }

            return plataformList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao adaptar lista de plataformas", e);
        }
    }
}
