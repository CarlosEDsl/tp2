package com.TP.account_service.handlers;

import com.TP.account_service.strategies.GoogleLoginStrategy;
import com.TP.account_service.strategies.LoginStrategy;
import org.springframework.stereotype.Component;

@Component
public class GoogleLoginHandler extends LoginHandler {
    private final GoogleLoginStrategy googleLoginStrategy;

    public GoogleLoginHandler(GoogleLoginStrategy googleLoginStrategy) {
        this.googleLoginStrategy = googleLoginStrategy;
    }

    @Override
    public LoginStrategy createLoginStrategy() {
        return googleLoginStrategy;
    }
}
