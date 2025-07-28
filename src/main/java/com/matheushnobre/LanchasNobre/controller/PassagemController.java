package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.dto.PassagemDTO;
import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.service.PassagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passagem")
public class PassagemController {

    @Autowired
    private PassagemService passagemService;

    @PostMapping
    public ResponseEntity<Passagem> add(@RequestBody @Valid PassagemDTO passagemDTO) {
        Passagem passagemCriada = passagemService.add(passagemDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(passagemCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passagem> getById(@PathVariable Long id) {
        Passagem passagem = passagemService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(passagem);
    }
}
