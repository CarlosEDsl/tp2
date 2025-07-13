package com.TP.game_service.builders;

import com.TP.game_service.interfaces.IRawgBuilder;
import com.TP.game_service.interfaces.IRawgGameBuilder;
import org.springframework.stereotype.Component;

@Component
public class GamesUrlBuilder implements IRawgBuilder, IRawgGameBuilder {
    private StringBuilder url;
    private String apiKey;

    public GamesUrlBuilder() {
        url = new StringBuilder();
    }

    @Override
    public void reset() {
        url = new StringBuilder();
    }

    @Override
    public void setBaseUrl() {
        url.append("https://api.rawg.io/api/games?");
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getById(Long id) {
        url.setLength(0);
        url.append("https://api.rawg.io/api/games/")
                .append(id)
                .append("?key=")
                .append(apiKey);
        String response = url.toString();
        reset();

        return response;
    }

    @Override
    public void page(int page) {
        url.append("&page=").append(page);
    }

    @Override
    public void pageSize(int pageSize) {
        url.append("&page_size=").append(pageSize).append("&");
    }

    @Override
    public void genre(String genre) {
        url.append("&genres=").append(genre).append("&");
    }

    @Override
    public void platform(Long platformId) {
        url.append("&platforms=").append(platformId).append("&");
    }

    @Override
    public void search(String query) {
        url.append("&search=").append(query).append("&");
    }

    @Override
    public void ordering(String order) {
        url.append("&ordering=-").append(order).append("&");
    }

    @Override
    public void stores(int storeId) {
        url.append("&stores=").append(storeId).append("&");
    }

    @Override
    public void searchExact(Boolean query) {
        url.append("&search_exact=").append(query).append("&");
    }

    @Override
    public void searchPrecise(Boolean query) {
        url.append("&search_precise=").append(query).append("&");
    }

    @Override
    public void metacritic(String metacritic) { url.append("&metacritic=").append(metacritic).append("&"); }

    @Override
    public void excludeAdditions(boolean excludeAdditions) { url.append("&exclude_additions=").append(excludeAdditions).append("&"); }

    @Override
    public String build() {
        url.append("&key=").append(apiKey);
        String response = url.toString();
        reset();

        return response;
    }
}
