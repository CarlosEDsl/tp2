package com.TP.game_service.models.DTOs;

import org.springframework.validation.annotation.Validated;

import java.util.UUID;

public record FavoriteGameRequestDTO (
        Long gameId
) { }
