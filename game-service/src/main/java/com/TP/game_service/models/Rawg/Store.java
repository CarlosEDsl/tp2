package com.TP.game_service.models.Rawg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private Integer id;
    private String name;
    private String slug;
    private String domain;
    private Integer gamesCount;
    private String imageBackground;
}
