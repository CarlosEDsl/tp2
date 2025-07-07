package com.TP.review_service.models.DTO;

import java.util.UUID;

public record RateDTO (UUID postId, UUID userId, double rate) {
}
