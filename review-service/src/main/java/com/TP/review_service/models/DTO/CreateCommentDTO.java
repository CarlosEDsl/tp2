package com.TP.review_service.models.DTO;

import java.util.UUID;

public record CreateCommentDTO(UUID userId,
       UUID postId,
       String content
) { }
