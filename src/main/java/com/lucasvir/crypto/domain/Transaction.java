package com.lucasvir.crypto.domain;

import com.lucasvir.crypto.dto.TransactionUpdateDto;
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
public class Transaction implements Serializable {

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

    public void updateData(TransactionUpdateDto dto) {
        this.userDocument = getUserDocument();
        this.creditCardToken = getCreditCardToken();
        this.value = dto.value() != null ? dto.value() : getValue();
    }

}
