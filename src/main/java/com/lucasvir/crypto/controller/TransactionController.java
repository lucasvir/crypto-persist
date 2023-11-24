package com.lucasvir.crypto.controller;

import com.lucasvir.crypto.domain.Transaction;
import com.lucasvir.crypto.dto.TransactionCreateDto;
import com.lucasvir.crypto.dto.TransactionDataDto;
import com.lucasvir.crypto.dto.TransactionUpdateDto;
import com.lucasvir.crypto.services.TransactionService;
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
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<List<TransactionDataDto>> index() {
        List<TransactionDataDto> users = service.index();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionDataDto> show(@PathVariable Long id) {
        TransactionDataDto user = service.show(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TransactionDataDto> create(@RequestBody @Valid TransactionCreateDto dto) {
        Transaction transaction = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transaction.getId()).toUri();

        TransactionDataDto userDto = new TransactionDataDto(transaction.getId(), dto.userDocument(), dto.creditCardToken(), dto.value());

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<TransactionDataDto> update(@PathVariable Long id, @RequestBody @Valid TransactionUpdateDto dto) {
        TransactionDataDto user = service.update(id, dto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleteado com sucesso");
    }
}
