package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.service.LanchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lancha")
public class LanchaController {
    @Autowired
    private LanchaService lanchaService;

    @PostMapping
    public ResponseEntity<Lancha> adicionar(@RequestBody Lancha lancha) {
        Lancha lanchaCriada = lanchaService.salvar(lancha);
        return ResponseEntity.status(HttpStatus.CREATED).body(lanchaCriada);
    }

    @GetMapping
    public ResponseEntity<List<Lancha>> listarTodas() {
        List<Lancha> lista = lanchaService.listarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Lancha> atualizar(@PathVariable Long id, @RequestBody Lancha lancha) {
        Lancha lanchaAtualizada = lanchaService.atualizar(id, lancha);
        return ResponseEntity.status(HttpStatus.OK).body(lanchaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        boolean foiDeletado = lanchaService.deletarPorId(id);
        if(foiDeletado) return ResponseEntity.noContent().build(); // 204 noContent (deletado)
        return ResponseEntity.notFound().build(); // 404 notFound
    }
}
