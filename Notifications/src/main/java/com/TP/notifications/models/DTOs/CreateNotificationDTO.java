package com.TP.notifications.models.DTOs;

import lombok.NonNull;

import java.util.UUID;

public record CreateNotificationDTO(
        @NonNull UUID receiverId,
        @NonNull UUID senderId,
        @NonNull String type,
        String redirect
) {}