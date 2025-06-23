package com.TP.game_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private Long id;
    private String name;
    private String released;
    private float rating;
    private int ratings_count;
    private String background_image;
    private List<Plataform> plataforms;
    private List<Genre> genres;
}
