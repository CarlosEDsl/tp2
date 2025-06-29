package com.TP.game_service.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if ("gameId".equals(ex.getName())) {
            return ResponseEntity.badRequest().body("O parâmetro 'gameId' deve ser um número válido.");
        }
        return ResponseEntity.badRequest().body("Parâmetro inválido.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        if ("gameId".equals(ex.getParameterName())) {
            return ResponseEntity.badRequest().body("O parâmetro 'gameId' não pode ser vazio.");
        }
        return ResponseEntity.badRequest().body("Parâmetro inválido.");
    }
}
