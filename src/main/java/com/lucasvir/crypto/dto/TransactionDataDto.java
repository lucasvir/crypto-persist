package com.lucasvir.crypto.dto;

import com.lucasvir.crypto.domain.Transaction;

public record TransactionDataDto(
        Long id,
        String userDocument,
        String creditCardToken,
        Long value
) {
    public TransactionDataDto(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getUserDocument(),
                transaction.getCreditCardToken(),
                transaction.getValue()
        );
    }
}
