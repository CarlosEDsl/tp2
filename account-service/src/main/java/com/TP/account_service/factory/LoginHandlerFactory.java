package com.TP.account_service.factory;

import com.TP.account_service.handlers.GoogleLoginHandler;
import com.TP.account_service.handlers.LoginHandler;
import com.TP.account_service.handlers.NormalLoginHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginHandlerFactory {

    private final NormalLoginHandler normalLoginHandler;
    private final GoogleLoginHandler googleLoginHandler;

    public LoginHandlerFactory(NormalLoginHandler normalLoginHandler, GoogleLoginHandler googleLoginHandler) {
        this.normalLoginHandler = normalLoginHandler;
        this.googleLoginHandler = googleLoginHandler;
    }

    public LoginHandler getLoginStrategy(String provider) {
        switch(provider.toUpperCase()){
            case "GOOGLE":
                return googleLoginHandler;
            case "X":
            default:
                return normalLoginHandler;
        }
    }
}
