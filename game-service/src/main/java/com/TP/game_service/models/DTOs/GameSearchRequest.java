package com.TP.game_service.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameSearchRequest {
    private int page = 1;
    private int pageSize = 50;
    private String search;
    private String genre;
    private Long platform;
    private String ordering;
}
