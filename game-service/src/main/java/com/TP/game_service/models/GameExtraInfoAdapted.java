package com.TP.game_service.models;

import com.TP.game_service.models.Rawg.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameExtraInfoAdapted {
    private Long id;
    private String name;
    private String description;
    private String released;
    private Double rating;
    private int ratings_count;
    private int achievementsCount;
    private String website;
    private String background_image;
    private List<PlatformAdapted> platforms;
    private List<GenreAdapted> genres;
    private List<StoreAdapted> stores;
}
