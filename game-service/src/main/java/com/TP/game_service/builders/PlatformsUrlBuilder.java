package com.TP.game_service.builders;

public class PlatformsUrlBuilder {
    private final StringBuilder url;
    private String rawgApiKey;

    public PlatformsUrlBuilder(String apiKey) {
        url = new StringBuilder("https://api.rawg.io/api/platforms?");
        this.rawgApiKey = apiKey;
    }

    public PlatformsUrlBuilder getById(Long id) {
        url.setLength(0);
        url.append("https://api.rawg.io/api/platforms/")
                .append(id)
                .append("?");
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
