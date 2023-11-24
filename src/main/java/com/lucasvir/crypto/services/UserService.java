package com.lucasvir.crypto.services;

import com.google.common.hash.Hashing;
import com.lucasvir.crypto.domain.User;
import com.lucasvir.crypto.dto.UserCreateDto;
import com.lucasvir.crypto.dto.UserDataDto;
import com.lucasvir.crypto.dto.UserUpdateDto;
import com.lucasvir.crypto.repositories.UserRepository;
import com.lucasvir.crypto.utils.SecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityComponents security;

    public List<UserDataDto> index() {

        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("Não ha registros");
        }

        List<UserDataDto> usersDtoList = new ArrayList<>();
        users.forEach(u -> usersDtoList.add(
                new UserDataDto(
                        u.getId(),
                        security.decrypt(u.getUserDocument()),
                        security.decrypt(u.getCreditCardToken()),
                        u.getValue()
                ))
        );

        return usersDtoList;
    }

    public User create(UserCreateDto dto) {
        String encryptedDoc = security.encrypt(dto.userDocument());
        String encryptedToken = security.encrypt(dto.creditCardToken());
        User user = new User(null, encryptedDoc, encryptedToken, dto.value());

        repository.save(user);

        return user;
    }

    public UserDataDto update(Long id, UserUpdateDto dto) {
        boolean dtoIsEmpty = dto.userDocument() == null &&
                dto.creditCardToken() == null &&
                dto.value() == null;

        if (dtoIsEmpty) {
            throw new RuntimeException("Nenhum dado foi inserido para atualizacao.");
        }

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontado."));

        if (dto.userDocument() != null) {
            String encryptedDoc = security.encrypt(dto.userDocument());
            user.setUserDocument(encryptedDoc);
        }

        if (dto.creditCardToken() != null) {
            String ecnryptedCardToken = security.encrypt(dto.creditCardToken());
            user.setCreditCardToken(ecnryptedCardToken);
        }

        user.updateData(dto);
        repository.save(user);

        String hasDoc = dto.userDocument() != null ? dto.userDocument() : security.decrypt(user.getUserDocument());
        String hasToken = dto.creditCardToken() != null ? dto.creditCardToken() : security.decrypt(user.getCreditCardToken());
        Long hasValue = dto.value() != null ? dto.value() : user.getValue();

        return new UserDataDto(id, hasDoc, hasToken, hasValue);
    }

    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontrado"));

        repository.delete(user);
    }

    public UserDataDto show(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontrado"));

        return new UserDataDto(
                user.getId(),
                security.decrypt(user.getUserDocument()),
                security.decrypt(user.getCreditCardToken()),
                user.getValue()
        );
    }
}
