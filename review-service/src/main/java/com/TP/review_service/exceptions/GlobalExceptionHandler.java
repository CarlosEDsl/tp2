package com.TP.review_service.exceptions;

import com.TP.review_service.exceptions.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Token don't have enough permission to access this",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(BusinessRuleException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Business Rule Violation",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(InvalidRequestException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
