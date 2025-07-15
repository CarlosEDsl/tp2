package com.TP.account_service.handlers;

import com.TP.account_service.strategies.LoginStrategy;
import com.TP.account_service.strategies.NormalLoginStrategy;
import org.springframework.stereotype.Component;

@Component
public class NormalLoginHandler extends LoginHandler {

    private final NormalLoginStrategy normalLoginStrategy;

    public NormalLoginHandler(NormalLoginStrategy normalLoginStrategy) {
        this.normalLoginStrategy = normalLoginStrategy;
    }

    @Override
    public LoginStrategy createLoginStrategy() {
        return normalLoginStrategy;
    }
}
