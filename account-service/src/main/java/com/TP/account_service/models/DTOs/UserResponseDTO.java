package com.TP.account_service.models.DTOs;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        String email,
        String avatarUrl,
        String bio
) {}