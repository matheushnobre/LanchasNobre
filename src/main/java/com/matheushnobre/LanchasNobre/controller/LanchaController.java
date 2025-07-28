package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.dto.LanchaDTO;
import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.service.LanchaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lanchas")
public class LanchaController {
    @Autowired
    private LanchaService lanchaService;

    @PostMapping
    public ResponseEntity<Lancha> add(@RequestBody @Valid LanchaDTO lanchaDTO) {
        Lancha lancha = lanchaService.add(lanchaDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(lancha);
    }

    @GetMapping
    public ResponseEntity<List<Lancha>> listAll() {
        List<Lancha> lista = lanchaService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancha> getById(@PathVariable Long id) {
        Lancha lancha = lanchaService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(lancha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lancha> updateById(@PathVariable Long id, @RequestBody @Valid LanchaDTO lanchaDTO) {
        Lancha lancha = lanchaService.updateById(id, lanchaDTO.toEntity());
        return ResponseEntity.status(HttpStatus.OK).body(lancha);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        lanchaService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 noContent (deletado)
    }
}
