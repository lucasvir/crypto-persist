package com.lucasvir.crypto.services;

import com.lucasvir.crypto.domain.Transaction;
import com.lucasvir.crypto.dto.TransactionCreateDto;
import com.lucasvir.crypto.dto.TransactionDataDto;
import com.lucasvir.crypto.dto.TransactionUpdateDto;
import com.lucasvir.crypto.repositories.TransactionRepository;
import com.lucasvir.crypto.utils.SecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private SecurityComponents security;

    public List<TransactionDataDto> index() {

        List<Transaction> transactions = repository.findAll();
        if (transactions.isEmpty()) {
            throw new RuntimeException("Não ha registros");
        }

        List<TransactionDataDto> usersDtoList = new ArrayList<>();
        transactions.forEach(u -> usersDtoList.add(
                new TransactionDataDto(
                        u.getId(),
                        security.decrypt(u.getUserDocument()),
                        security.decrypt(u.getCreditCardToken()),
                        u.getValue()
                ))
        );

        return usersDtoList;
    }

    public Transaction create(TransactionCreateDto dto) {
        String encryptedDoc = security.encrypt(dto.userDocument());
        String encryptedToken = security.encrypt(dto.creditCardToken());
        Transaction transaction = new Transaction(null, encryptedDoc, encryptedToken, dto.value());

        repository.save(transaction);

        return transaction;
    }

    public TransactionDataDto update(Long id, TransactionUpdateDto dto) {
        boolean dtoIsEmpty = dto.userDocument() == null &&
                dto.creditCardToken() == null &&
                dto.value() == null;

        if (dtoIsEmpty) {
            throw new RuntimeException("Nenhum dado foi inserido para atualizacao.");
        }

        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontado."));

        if (dto.userDocument() != null) {
            String encryptedDoc = security.encrypt(dto.userDocument());
            transaction.setUserDocument(encryptedDoc);
        }

        if (dto.creditCardToken() != null) {
            String ecnryptedCardToken = security.encrypt(dto.creditCardToken());
            transaction.setCreditCardToken(ecnryptedCardToken);
        }

        transaction.updateData(dto);
        repository.save(transaction);

        String hasDoc = dto.userDocument() != null ? dto.userDocument() : security.decrypt(transaction.getUserDocument());
        String hasToken = dto.creditCardToken() != null ? dto.creditCardToken() : security.decrypt(transaction.getCreditCardToken());
        Long hasValue = dto.value() != null ? dto.value() : transaction.getValue();

        return new TransactionDataDto(id, hasDoc, hasToken, hasValue);
    }

    public void delete(Long id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontrado"));

        repository.delete(transaction);
    }

    public TransactionDataDto show(Long id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontrado"));

        return new TransactionDataDto(
                transaction.getId(),
                security.decrypt(transaction.getUserDocument()),
                security.decrypt(transaction.getCreditCardToken()),
                transaction.getValue()
        );
    }
}
