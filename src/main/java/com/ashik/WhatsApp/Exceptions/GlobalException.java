package com.ashik.WhatsApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException e , WebRequest req){

        ErrorDetails err = new ErrorDetails(e.getMessage(),req.getDescription(false), LocalDateTime.now());

        return  new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetails> ChatExceptionHandler(ChatException e , WebRequest req){

        ErrorDetails err = new ErrorDetails(e.getMessage(),req.getDescription(false), LocalDateTime.now());

        return  new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> OtherExceptionHandler(Exception e , WebRequest req){

        ErrorDetails err = new ErrorDetails(e.getMessage(),req.getDescription(false), LocalDateTime.now());

        return  new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);

    }



}
