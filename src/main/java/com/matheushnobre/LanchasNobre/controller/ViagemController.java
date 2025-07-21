package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.service.ViagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagem")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @PostMapping
    public ResponseEntity<Viagem> adicionar(@Valid @RequestBody Viagem viagem) {
        Viagem viagemSalva = viagemService.salvar(viagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemSalva);
    }

    @GetMapping
    public ResponseEntity<List<Viagem>> listarTodas() {
        List<Viagem> lista = viagemService.listarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Viagem> atualizar(@PathVariable Long id, @Valid @RequestBody Viagem viagem){
        Viagem viagemAtualizada = viagemService.atualizar(id, viagem);
        return ResponseEntity.status(HttpStatus.OK).body(viagemAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        boolean foiDeletado = viagemService.deletarPorId(id);
        if(foiDeletado) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
