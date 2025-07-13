package com.TP.game_service.interfaces;

import org.springframework.stereotype.Component;

public interface IRawgBuilder {
    void reset();
    void setBaseUrl();
    void setApiKey(String apiKey);
    String getById(Long id);
    void page(int page);
    void pageSize(int pageSize);
    void ordering(String order);
    String build();
}
