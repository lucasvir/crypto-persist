package com.lucasvir.crypto.dto;

import com.lucasvir.crypto.domain.User;
public record UserDataDto(
        Long id,
        String userDocument,
        String creditCardToken,
        Long value
) {
    public UserDataDto(User user) {
        this(
                user.getId(),
                user.getUserDocument(),
                user.getCreditCardToken(),
                user.getValue()
        );
    }
}
