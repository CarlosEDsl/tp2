package com.TP.review_service.models.DTO;

import java.util.UUID;

public record UpdatePostDTO(
        long gameId,
        String title,
        String content,
        String imageURL
) {}