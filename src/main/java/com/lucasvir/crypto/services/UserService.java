package com.lucasvir.crypto.services;

import com.google.common.hash.Hashing;
import com.lucasvir.crypto.domain.User;
import com.lucasvir.crypto.dto.UserCreateDto;
import com.lucasvir.crypto.dto.UserDataDto;
import com.lucasvir.crypto.dto.UserUpdateDto;
import com.lucasvir.crypto.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserDataDto> index() {

        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("Não ha registros");
        }

        List<UserDataDto> usersDtoList = new ArrayList<>();
        users.forEach(u -> usersDtoList.add(new UserDataDto(u)));

        return usersDtoList;
    }

    public User create(UserCreateDto dto) {
        return repository.save(new User(dto));
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

        user.updateData(dto);
        repository.save(user);

        return new UserDataDto(user);
    }

    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontrado"));

        repository.delete(user);
    }

    public UserDataDto show(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id nao encontrado"));

        return new UserDataDto(user);
    }
}
