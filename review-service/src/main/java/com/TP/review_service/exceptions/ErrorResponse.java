package com.TP.review_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(Instant timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

}

