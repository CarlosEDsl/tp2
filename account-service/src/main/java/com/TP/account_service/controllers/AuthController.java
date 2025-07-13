package com.TP.account_service.controllers;

import com.TP.account_service.factory.LoginHandlerFactory;
import com.TP.account_service.handlers.LoginHandler;
import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.models.DTOs.AuthResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginHandlerFactory loginHandlerFactory;

    public AuthController(LoginHandlerFactory loginHandlerFactory) {
        this.loginHandlerFactory = loginHandlerFactory;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO dto) {
        try {
            LoginHandler loginHandler = loginHandlerFactory.getLoginStrategy(dto.provider());
            AuthResponseDTO authResponse = loginHandler.login(dto);

            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}