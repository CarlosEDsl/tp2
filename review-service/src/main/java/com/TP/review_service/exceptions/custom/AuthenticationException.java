package com.TP.review_service.exceptions.custom;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

}
