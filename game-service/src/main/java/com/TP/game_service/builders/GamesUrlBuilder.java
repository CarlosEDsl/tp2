package com.TP.game_service.builders;

public class GamesUrlBuilder {
    private final StringBuilder url;
    private String rawgApiKey;

    public GamesUrlBuilder(String apiKey) {
        url = new StringBuilder("https://api.rawg.io/api/games?");
        this.rawgApiKey = apiKey;
    }

    public GamesUrlBuilder getById(Long id) { //para passar o id do game
        url.setLength(0);
        url.append("https://api.rawg.io/api/games/")
                .append(id)
                .append("?");
        return this;
    }

    public GamesUrlBuilder page(int page) { //define a pagina
        url.append("&page=").append(page);
        return this;
    }

    public GamesUrlBuilder pageSize(int pageSize) { //define a quantidade por pagina
        url.append("&page_size=").append(pageSize).append("&");
        return this;
    }

    public GamesUrlBuilder genre(String genre) { //para buscar por genero
        url.append("&genres=").append(genre).append("&");
        return this;
    }

    public GamesUrlBuilder platform(Long platformId) { //para buscar por plataforma
        url.append("&platforms=").append(platformId).append("&");
        return this;
    }

    public GamesUrlBuilder search(String query) { //barra de pesquisa
        url.append("&search=").append(query).append("&");
        return this;
    }

    public GamesUrlBuilder ordering(String order) { //ordena os jogos em uma ordem baseado em uma variavel especifica
        url.append("&ordering=-").append(order).append("&");
        return this;
    }

    public String build() {
        url.append("&key=").append(rawgApiKey);
        return url.toString();
    }
}
