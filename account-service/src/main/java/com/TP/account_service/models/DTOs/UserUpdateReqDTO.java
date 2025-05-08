package com.TP.account_service.models.DTOs;

import java.time.Instant;

public record UserUpdateReqDTO(
    String username,
    String email,
    String passwordHash,
    String avatarUrl,
    String bio,
    Instant createdAt,
    Instant updatedAt
) { }
