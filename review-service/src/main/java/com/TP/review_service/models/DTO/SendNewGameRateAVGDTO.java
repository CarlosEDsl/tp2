package com.TP.review_service.models.DTO;

import java.util.UUID;

public record SendNewGameRateAVGDTO(
        Double rating,
        UUID gameId
) {
}
