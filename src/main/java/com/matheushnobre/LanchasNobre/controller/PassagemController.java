package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.service.PassagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passagem")
public class PassagemController {

    @Autowired
    private PassagemService passagemService;

    @PostMapping
    public ResponseEntity<Passagem> adicionar(@RequestBody Passagem passagem) {
        Passagem passagemCriada = passagemService.salvar(passagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(passagemCriada);
    }

    @GetMapping
    public ResponseEntity<List<Passagem>> listarTodas() {
        List<Passagem> lista = passagemService.listarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passagem> selecionarPorId(@PathVariable Long id) {
        Passagem passagem = passagemService.selecionarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(passagem);
    }
}
