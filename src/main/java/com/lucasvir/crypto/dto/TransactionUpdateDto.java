package com.lucasvir.crypto.dto;

public record TransactionUpdateDto(
        String userDocument,
        String creditCardToken,
        Long value
) {
}
