package com.TP.game_service.builders;

import com.TP.game_service.interfaces.IRawgBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StoresUrlBuilder implements IRawgBuilder {
    private StringBuilder url;
    private String apiKey;

    public StoresUrlBuilder() {
        url = new StringBuilder();
    }

    @Override
    public void reset() {
        url = new StringBuilder();
    }

    @Override
    public void setBaseUrl() {
        url.append("https://api.rawg.io/api/stores?");
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getById(Long id) {
        url.setLength(0);
        url.append("https://api.rawg.io/api/stores/")
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
    public void ordering(String order) {
        url.append("&ordering=-").append(order).append("&");
    }

    @Override
    public String build() {
        url.append("&key=").append(apiKey);
        String response = url.toString();
        reset();
        setBaseUrl();

        return response;
    }
}
