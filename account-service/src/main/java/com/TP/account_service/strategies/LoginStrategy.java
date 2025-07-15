package com.TP.account_service.strategies;

import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.models.DTOs.AuthResponseDTO;

public interface LoginStrategy {
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
}
