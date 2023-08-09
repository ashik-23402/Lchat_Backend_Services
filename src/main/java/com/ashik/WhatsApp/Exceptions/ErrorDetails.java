package com.ashik.WhatsApp.Exceptions;

import java.time.LocalDateTime;

public class ErrorDetails {

    private  String error;
    private String message;
    private LocalDateTime timestamp;

    ErrorDetails(String error, String message, LocalDateTime timestamp){
        super();
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }
}
