package com.lucasvir.crypto.controller;

import com.lucasvir.crypto.domain.User;
import com.lucasvir.crypto.dto.UserCreateDto;
import com.lucasvir.crypto.dto.UserDataDto;
import com.lucasvir.crypto.dto.UserUpdateDto;
import com.lucasvir.crypto.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/crypto")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDataDto>> index() {
        try {
            List<UserDataDto> users = service.index();
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            System.out.printf(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDataDto> show(@PathVariable Long id) {
        try {
            UserDataDto user = service.show(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDataDto> create(@RequestBody @Valid UserCreateDto dto) {
        User user = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

        UserDataDto userDto = new UserDataDto(user.getId(), dto.userDocument(), dto.creditCardToken(), dto.value());

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<UserDataDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto dto) {
        try {
            UserDataDto user = service.update(id, dto);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleteado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
