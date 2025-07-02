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
public class PlatformComplete {
    private Long id;
    private String name;
    private String slug;
    @JsonProperty("games_count")
    private Integer gamesCount;
    @JsonProperty("image_background")
    private String imageBackground;
    private String image;
    @JsonProperty("year_end")
    private Integer yearEnd;
    @JsonProperty("year_start")
    private Integer yearStart;
    private List<Game> games;
}
