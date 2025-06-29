package com.TP.game_service.builders;

import org.springframework.beans.factory.annotation.Value;

public class GenresUrlBuilder {
    private StringBuilder url;
    private String rawgApiKey;

    public GenresUrlBuilder(String rawgApiKey) {
        url = new StringBuilder("https://api.rawg.io/api/genres?");
        this.rawgApiKey = rawgApiKey;
    }

    public GenresUrlBuilder getById(Long id) {
        url.setLength(0);
        url.append("https://api.rawg.io/api/genres/")
                .append(id)
                .append("?")
                .append(rawgApiKey);
        return this;
    }

    public GenresUrlBuilder page(int page) {
        url.append("&page=").append(page);
        return this;
    }

    public GenresUrlBuilder pageSize(int pageSize) {
        url.append("&page_size=").append(pageSize).append("&");
        return this;
    }

    public GenresUrlBuilder ordering(String order) {
        url.append("&ordering=-").append(order).append("&");
        return this;
    }

    public String build() {
        url.append("&key=").append(rawgApiKey);
        return url.toString();
    }
}
