package com.TP.review_service.models.DTO;

import java.util.UUID;

public record LikeDTO (UUID userId, UUID postId) {
}
