package com.TP.game_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GameAdapted {
    private Long id;
    private String name;
    private String released;
    private Double rating;
    private int ratings_count;
    private String background_image;
    private List<PlatformAdapted> platforms;
    private List<GenreAdapted> genres;
    private List<StoreAdapted> stores;
}
