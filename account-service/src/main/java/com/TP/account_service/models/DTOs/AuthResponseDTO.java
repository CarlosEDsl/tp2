package com.TP.account_service.models.DTOs;

import com.TP.account_service.models.User;

public record AuthResponseDTO(String token, User user) {
}
