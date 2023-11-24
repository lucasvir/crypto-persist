package com.lucasvir.crypto.dto;

import jakarta.validation.constraints.NotNull;

public record TransactionCreateDto(
        @NotNull
        String userDocument,
        @NotNull
        String creditCardToken,
        @NotNull
        Long value
) {
}
