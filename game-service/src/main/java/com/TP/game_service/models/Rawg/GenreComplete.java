package com.TP.game_service.models.Rawg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreComplete {
    private Long id;
    private String name;
    private String slug;
    @JsonProperty("games_count")
    private Integer gamesCount;
    @JsonProperty("image_background")
    private String imageBackground;
    private List<Game> games;
}
