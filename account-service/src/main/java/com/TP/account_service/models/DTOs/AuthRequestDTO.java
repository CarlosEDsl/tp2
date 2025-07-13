package com.TP.account_service.models.DTOs;

public record AuthRequestDTO(String email, String password, String token, String provider) {
}
