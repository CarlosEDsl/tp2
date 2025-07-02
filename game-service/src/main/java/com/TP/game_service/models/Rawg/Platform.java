package com.TP.game_service.models.Rawg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Platform {
    private Long id;
    private String name;
    private String slug;
    private String image;
    private Integer yearEnd;
    private Integer yearStart;
    private Integer gamesCount;
    private String imageBackground;
}
