package com.TP.account_service.loginStrategies.strategies;

import com.TP.account_service.loginStrategies.LoginStrategy;
import com.TP.account_service.models.DTOs.AuthRequestDTO;
import com.TP.account_service.models.DTOs.AuthResponseDTO;
import com.TP.account_service.models.User;
import com.TP.account_service.security.JwtService;
import com.TP.account_service.services.UserService;
import com.TP.account_service.utils.GoogleTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.stereotype.Component;

@Component
public class GoogleLoginStrategy implements LoginStrategy {

    private final JwtService jwtService;
    private final UserService userService;

    public GoogleLoginStrategy(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authDTO) {
        GoogleIdToken.Payload payload = GoogleTokenVerifier.verifyGoogleToken(authDTO.token());
        if (payload == null) {
            throw new RuntimeException("ID Token inválido");
        }

        // Salva ou atualiza no banco
        User user = userService.getOrSaveGoogleAccount(payload);
        // Gera o TOKEN
        String token = jwtService.generateToken(user.getEmail(), user.getId());

        return new AuthResponseDTO("Bearer " + token, user);

    }
}
