package com.TP.game_service.util.customExceptions;

public class GameAlreadyFavoritedException extends RuntimeException {
    public GameAlreadyFavoritedException(String message) {
        super(message);
    }
}
