package com.lucasvir.crypto.domain;

import com.google.common.hash.Hashing;
import com.lucasvir.crypto.dto.UserCreateDto;
import com.lucasvir.crypto.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Entity
@Table(name = "users")
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

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public void updateData(UserUpdateDto dto) {
        this.userDocument = getUserDocument();
        this.creditCardToken = getCreditCardToken();
        this.value = dto.value() != null ? dto.value() : getValue();
    }

}
