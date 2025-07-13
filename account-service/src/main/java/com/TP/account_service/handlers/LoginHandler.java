package com.TP.account_service.handlers;

import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.models.DTOs.AuthResponseDTO;
import com.TP.account_service.strategies.LoginStrategy;

public abstract class LoginHandler {
    public abstract LoginStrategy createLoginStrategy();

    public AuthResponseDTO login(AuthRequestDTO dto) {
        LoginStrategy strategy = createLoginStrategy();
        return strategy.login(dto);
    }
}
