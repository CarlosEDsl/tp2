package com.TP.account_service.models.DTOs;

public record UserRequestDTO(
        String username,
        String email,
        String password,
        String avatarUrl,
        String bio
) { }
