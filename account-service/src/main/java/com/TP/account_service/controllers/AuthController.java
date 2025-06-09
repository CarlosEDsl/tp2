package com.TP.account_service.controllers;

import com.TP.account_service.loginStrategies.LoginStrategiesFactory;
import com.TP.account_service.loginStrategies.LoginStrategy;
import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.models.DTOs.AuthResponseDTO;
import com.TP.account_service.security.JwtService;
import com.TP.account_service.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginStrategiesFactory loginStrategiesFactory;

    public AuthController(LoginStrategiesFactory loginStrategiesFactory) {
        this.loginStrategiesFactory = loginStrategiesFactory;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO dto) {
        try {
            LoginStrategy loginStrategy = loginStrategiesFactory.getLoginStrategy(dto.provider());
            String token = loginStrategy.login(dto);

            return ResponseEntity.ok(new AuthResponseDTO("Bearer " + token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}