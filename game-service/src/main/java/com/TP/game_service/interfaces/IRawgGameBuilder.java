package com.TP.game_service.interfaces;

public interface IRawgGameBuilder {
    void genre(String genre);
    void platform(Long platformId);
    void search(String query);
    void stores(int storeId);
    void searchExact(Boolean query);
    void searchPrecise(Boolean query);
}
