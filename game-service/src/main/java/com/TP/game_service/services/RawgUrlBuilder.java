package com.TP.game_service.services;

import java.util.Properties;

public class RawgUrlBuilder {
    private final StringBuilder url;
    private Properties props = new Properties();

    public RawgUrlBuilder() {
        url = new StringBuilder("https://api.rawg.io/api/");
    }

    public RawgUrlBuilder page(int page) {
        url.append("games?page=").append(page);
        return this;
    }

    public RawgUrlBuilder genre(String genre) {
        url.append("games?genre=").append(genre);
        return this;
    }

    public RawgUrlBuilder search(String query) {
        url.append("games?search=").append(query);
        return this;
    }

    public String build() {
        url.append(props.getProperty("&" + "rawg.api.key"));
        return url.toString();
    }
}
