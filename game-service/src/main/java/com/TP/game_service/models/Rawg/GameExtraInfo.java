package com.TP.game_service.models.Rawg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameExtraInfo {
    private Long id;
    private String slug;
    private String name;

    @JsonProperty("name_original")
    private String nameOriginal;

    private String description;
    private int metacritic;

    @JsonProperty("metacritic_platforms")
    private List<MetacriticPlatform> metacriticPlatforms;

    private String released;
    private Boolean tba;
    private String updated;

    @JsonProperty("background_image")
    private String backgroundImage;

    @JsonProperty("background_image_additional")
    private String backgroundImageAdditional;

    private String website;
    private Double rating;

    @JsonProperty("rating_top")
    private int ratingTop;

    private List<Rating> ratings;
    private Map<String, Integer> reactions;
    private int added;

    @JsonProperty("added_by_status")
    private AddedByStatus addedByStatus;

    private int playtime;

    @JsonProperty("screenshots_count")
    private int screenshotsCount;

    @JsonProperty("movies_count")
    private int moviesCount;

    @JsonProperty("creators_count")
    private int creatorsCount;

    @JsonProperty("achievements_count")
    private int achievementsCount;

    @JsonProperty("parent_achievements_count")
    private int parentAchievementsCount;

    @JsonProperty("reddit_url")
    private String redditUrl;

    @JsonProperty("reddit_name")
    private String redditName;

    @JsonProperty("reddit_description")
    private String redditDescription;

    @JsonProperty("reddit_logo")
    private String redditLogo;

    @JsonProperty("reddit_count")
    private int redditCount;

    @JsonProperty("twitch_count")
    private int twitchCount;

    @JsonProperty("youtube_count")
    private int youtubeCount;

    @JsonProperty("reviews_text_count")
    private int reviewsTextCount;

    @JsonProperty("ratings_count")
    private int ratingsCount;

    @JsonProperty("suggestions_count")
    private int suggestionsCount;

    @JsonProperty("alternative_names")
    private List<String> alternativeNames;

    @JsonProperty("metacritic_url")
    private String metacriticUrl;

    @JsonProperty("parents_count")
    private int parentsCount;

    @JsonProperty("additions_count")
    private int additionsCount;

    @JsonProperty("game_series_count")
    private int gameSeriesCount;

    @JsonProperty("user_game")
    private Object userGame;

    @JsonProperty("reviews_count")
    private int reviewsCount;

    @JsonProperty("saturated_color")
    private String saturatedColor;

    @JsonProperty("dominant_color")
    private String dominantColor;

    @JsonProperty("parent_platforms")
    private List<ParentPlatform> parentPlatforms;

    private List<PlatformInfo> platforms;
    private List<StoreInfo> stores;
    private List<Developer> developers;
    private List<Genre> genres;
    private List<Tag> tags;
    private List<Publisher> publishers;

    @JsonProperty("esrb_rating")
    private EsrbRating esrbRating;

    private Object clip;

    @JsonProperty("description_raw")
    private String descriptionRaw;
}
