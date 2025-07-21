package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade){
        Cidade cidadeSalva = cidadeService.salvar(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalva);
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> listarTodas(){
        List<Cidade> lista = cidadeService.listarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
}
