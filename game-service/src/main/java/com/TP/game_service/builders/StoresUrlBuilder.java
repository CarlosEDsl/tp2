package com.TP.game_service.builders;

public class StoresUrlBuilder {
    private StringBuilder url;
    private String rawgApiKey;

    public StoresUrlBuilder(String rawgApiKey) {
        url = new StringBuilder("https://api.rawg.io/api/stores?");
        this.rawgApiKey = rawgApiKey;
    }

    public StoresUrlBuilder getById(Long id) {
        url.setLength(0);
        url.append("https://api.rawg.io/api/stores/")
                .append(id)
                .append("?")
                .append(rawgApiKey);
        return this;
    }

    public StoresUrlBuilder page(int page) {
        url.append("&page=").append(page);
        return this;
    }

    public StoresUrlBuilder pageSize(int pageSize) {
        url.append("&page_size=").append(pageSize).append("&");
        return this;
    }

    public StoresUrlBuilder ordering(String order) {
        url.append("&ordering=-").append(order).append("&");
        return this;
    }

    public String build() {
        url.append("&key=").append(rawgApiKey);
        return url.toString();
    }
}
