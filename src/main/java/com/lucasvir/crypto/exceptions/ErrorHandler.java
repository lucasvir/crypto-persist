package com.lucasvir.crypto.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<String> handle404(RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(EncryptionFaildException.class)
    private ResponseEntity<String> handleCryptoException(EncryptionFaildException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleArgumentNotValid(MethodArgumentNotValidException e) {
        return ResponseEntity.status(e.getStatusCode()).body("Argument missing. Please verify inputs");
    }
}
