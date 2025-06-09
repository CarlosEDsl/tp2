package com.TP.account_service.loginStrategies;

import com.TP.account_service.loginStrategies.strategies.GoogleLoginStrategy;
import com.TP.account_service.loginStrategies.strategies.NormalLoginStrategy;
import org.springframework.stereotype.Component;

@Component
public class LoginStrategiesFactory {

    private final NormalLoginStrategy normalLoginStrategy;
    private final GoogleLoginStrategy googleLoginStrategy;

    public LoginStrategiesFactory(NormalLoginStrategy normalLoginStrategy, GoogleLoginStrategy googleLoginStrategy) {
        this.normalLoginStrategy = normalLoginStrategy;
        this.googleLoginStrategy = googleLoginStrategy;
    }

    public LoginStrategy getLoginStrategy(String provider) {
        switch(provider.toUpperCase()){
            case "GOOGLE":
                return googleLoginStrategy;
            case "X":
            default:
                return normalLoginStrategy;
        }
    }
}
