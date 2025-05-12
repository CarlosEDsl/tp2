package com.TP.game_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

public class RawgUrlBuilder {
    private final StringBuilder url;
    private String rawgApiKey;

    public RawgUrlBuilder(String apiKey) {
        url = new StringBuilder("https://api.rawg.io/api/");
        this.rawgApiKey = apiKey;
    }

    public RawgUrlBuilder page(int page) {
        url.append("games?page=").append(page).append("&");
        return this;
    }

    public RawgUrlBuilder genre(String genre) {
        url.append("games?genre=").append(genre).append("&");
        return this;
    }

    public RawgUrlBuilder search(String query) {
        url.append("games?search=").append(query).append("&");
        return this;
    }

    public RawgUrlBuilder getById(Long id) {
        url.append("games/").append(id).append("?");
        return this;
    }

    public String build() {
        url.append("key=").append(rawgApiKey);
        return url.toString();
    }
}
