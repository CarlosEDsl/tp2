package com.TP.review_service.models.DTO;
import com.TP.review_service.models.enums.Rate;

import java.util.UUID;

public record CreatePostDTO(
        UUID authorId,
        UUID gameId,
        String title,
        String content,
        String imageURL,
        Double rate
) {}

