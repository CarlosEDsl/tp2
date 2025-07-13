package com.TP.account_service.loginStrategies.strategies;

import com.TP.account_service.loginStrategies.LoginStrategy;
import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.models.DTOs.AuthResponseDTO;
import com.TP.account_service.models.User;
import com.TP.account_service.security.JwtService;
import com.TP.account_service.security.UserDetailsImpl;
import com.TP.account_service.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NormalLoginStrategy implements LoginStrategy {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;

    public NormalLoginStrategy(AuthenticationManager authManager, JwtService jwtService, UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        // Faz a autenticação com o AuthenticationManager
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.email(), authRequestDTO.password())
        );

        // Obtém o UserDetails e o userId
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        UUID userId = userDetails.getUserId();

        // Gera o token com o userId e o email
        String token = jwtService.generateToken(authRequestDTO.email(), userId);
        User user = this.userService.findUser(userId);


        return new AuthResponseDTO("Bearer " + token, user);
    }
}
