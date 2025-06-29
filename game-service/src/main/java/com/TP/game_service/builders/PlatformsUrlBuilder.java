package com.TP.game_service.builders;

import org.springframework.beans.factory.annotation.Value;

public class PlatformsUrlBuilder {
    private StringBuilder url;
    private String rawgApiKey;

    public PlatformsUrlBuilder(String rawgApiKey) {
        url = new StringBuilder("https://api.rawg.io/api/platforms?");
        this.rawgApiKey = rawgApiKey;
    }

    public PlatformsUrlBuilder getById(Long id) {
        url.setLength(0);
        url.append("https://api.rawg.io/api/platforms/")
                .append(id)
                .append("?")
                .append(rawgApiKey);
        return this;
    }

    public PlatformsUrlBuilder page(int page) {
        url.append("&page=").append(page);
        return this;
    }

    public PlatformsUrlBuilder pageSize(int pageSize) {
        url.append("&page_size=").append(pageSize).append("&");
        return this;
    }

    public PlatformsUrlBuilder ordering(String order) {
        url.append("&ordering=-").append(order).append("&");
        return this;
    }

    public String build() {
        url.append("&key=").append(rawgApiKey);
        return url.toString();
    }
}
