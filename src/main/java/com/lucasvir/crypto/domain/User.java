package com.lucasvir.crypto.domain;

import com.google.common.hash.Hashing;
import com.lucasvir.crypto.dto.UserCreateDto;
import com.lucasvir.crypto.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userDocument;
    private String creditCardToken;
    private Long value;

    public User(UserCreateDto dto) {
        this.userDocument = hashSource(dto.userDocument());
        this.creditCardToken = hashSource(dto.creditCardToken());
        this.value = dto.value();
    }

    public void updateData(UserUpdateDto dto) {
        this.userDocument = dto.userDocument() != null ? hashSource(dto.userDocument()) : getUserDocument();
        this.creditCardToken = dto.creditCardToken() != null ? hashSource(dto.creditCardToken()) : getCreditCardToken();
        this.value = dto.value() != null ? dto.value() : getValue();
    }

    private String hashSource(String source) {
        return Hashing.sha256()
                .hashString(source, StandardCharsets.UTF_8)
                .toString();
    }

}
