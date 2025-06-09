package com.TP.account_service.loginStrategies;

import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.models.User;

public interface LoginStrategy {
    String login(AuthRequestDTO authRequestDTO);
}
