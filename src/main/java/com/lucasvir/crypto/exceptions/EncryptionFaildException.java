package com.lucasvir.crypto.exceptions;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EncryptionFaildException extends RuntimeException {

    private HttpStatus statusCode;

    public EncryptionFaildException(String msg, HttpStatus status) {
        super(msg);
        this.statusCode = status;
    }
}
