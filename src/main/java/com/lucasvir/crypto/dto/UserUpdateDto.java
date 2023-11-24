package com.lucasvir.crypto.dto;

public record UserUpdateDto(
        String userDocument,
        String creditCardToken,
        Long value
) {
}
