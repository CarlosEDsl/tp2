package com.TP.game_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

public class RawgUrlBuilder {
    private final StringBuilder url;
    private String rawgApiKey;

    public RawgUrlBuilder(String apiKey) {
        url = new StringBuilder("https://api.rawg.io/api/games");
        this.rawgApiKey = apiKey;
    }

    public RawgUrlBuilder page(int page) {
        url.append("?page=").append(page).append("&");
        return this;
    }

    public RawgUrlBuilder pageSize(int pageSize) {
        url.append("?page_size=").append(pageSize).append("&");
        return this;
    }

    public RawgUrlBuilder genre(String genre) {
        url.append("?genre=").append(genre).append("&");
        return this;
    }

    public RawgUrlBuilder search(String query) {
        url.append("?search=").append(query).append("&");
        return this;
    }

    public RawgUrlBuilder getById(Long id) {
        url.append("/").append(id).append("?");
        return this;
    }

    public String build() {
        url.append("key=").append(rawgApiKey);
        return url.toString();
    }
}
