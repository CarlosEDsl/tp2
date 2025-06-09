package com.TP.account_service.loginStrategies.strategies;

import com.TP.account_service.loginStrategies.LoginStrategy;
import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.security.JwtService;
import com.TP.account_service.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NormalLoginStrategy implements LoginStrategy {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public NormalLoginStrategy(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    public String login(AuthRequestDTO authRequestDTO) {
        // Faz a autenticação com o AuthenticationManager
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.email(), authRequestDTO.password())
        );

        // Obtém o UserDetails e o userId
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        UUID userId = userDetails.getUserId();

        // Gera o token com o userId e o email
        return jwtService.generateToken(authRequestDTO.email(), userId);
    }
}
