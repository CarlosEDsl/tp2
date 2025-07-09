package com.TP.review_service.models.DTO;

import java.util.UUID;

public record CreateNotificationDTO(
        UUID receiverId,
        UUID senderId,
        String type,
        String redirect
) {}
