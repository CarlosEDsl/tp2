package com.TP.game_service.models.Rawg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private Long id;
    private String slug;
    private String name;
    private String released;
    private Boolean tba;

    @JsonProperty("background_image")
    private String backgroundImage;

    private Double rating;

    @JsonProperty("rating_top")
    private int ratingTop;

    private List<Rating> ratings;

    @JsonProperty("ratings_count")
    private int ratingsCount;

    @JsonProperty("reviews_text_count")
    private int reviewsTextCount;

    private int added;

    @JsonProperty("added_by_status")
    private AddedByStatus addedByStatus;

    private int metacritic;
    private int playtime;

    @JsonProperty("suggestions_count")
    private int suggestionsCount;

    private String updated;

    @JsonProperty("user_game")
    private Object userGame;

    @JsonProperty("reviews_count")
    private int reviewsCount;

    @JsonProperty("saturated_color")
    private String saturatedColor;

    @JsonProperty("dominant_color")
    private String dominantColor;

    private List<PlatformInfo> platforms;

    @JsonProperty("parent_platforms")
    private List<ParentPlatform> parentPlatforms;

    private List<Genre> genres;
    private List<StoreInfo> stores;
    private Object clip;
    private List<Tag> tags;

    @JsonProperty("esrb_rating")
    private EsrbRating esrbRating;

    @JsonProperty("short_screenshots")
    private List<Screenshot> shortScreenshots;
}
