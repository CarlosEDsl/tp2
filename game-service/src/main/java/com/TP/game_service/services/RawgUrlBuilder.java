package com.TP.game_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

public class RawgUrlBuilder {
    private final StringBuilder url;
    private String rawgApiKey;

    public RawgUrlBuilder(String apiKey) {
        url = new StringBuilder("https://api.rawg.io/api/games?");
        this.rawgApiKey = apiKey;
    }

    public RawgUrlBuilder getById(Long id) { //para passar o id do game
        url.setLength(0);
        url.append("https://api.rawg.io/api/games/")
                .append(id)
                .append("?");
        return this;
    }

    public RawgUrlBuilder page(int page) { //define a pagina
        url.append("&page=").append(page);
        return this;
    }

    public RawgUrlBuilder pageSize(int pageSize) { //define a quantidade por pagina
        url.append("&page_size=").append(pageSize).append("&");
        return this;
    }

    public RawgUrlBuilder genre(String genre) { //para buscar por genero
        url.append("&genres=").append(genre).append("&");
        return this;
    }

    public RawgUrlBuilder platform(Long platformId) { //para buscar por plataforma
        url.append("&platforms=").append(platformId).append("&");
        return this;
    }

    public RawgUrlBuilder search(String query) { //barra de pesquisa
        url.append("&search=").append(query).append("&");
        return this;
    }

    public RawgUrlBuilder ordering(String order) { //ordena os jogos em uma ordem baseado em uma variavel especifica
        url.append("&ordering=-").append(order).append("&");
        return this;
    }

    public String build() {
        url.append("&key=").append(rawgApiKey);
        return url.toString();
    }
}
