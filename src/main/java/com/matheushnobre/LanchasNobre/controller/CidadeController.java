package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.dto.CidadeDTO;
import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<Cidade> add(@RequestBody @Valid CidadeDTO cidadeDTO){
        Cidade cidade = cidadeService.add(cidadeDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> listAll(){
        List<Cidade> lista = cidadeService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> updateById(@PathVariable Long id, @RequestBody @Valid CidadeDTO cidadeDTO){
        Cidade cidade = cidadeService.updateById(id, cidadeDTO.toEntity());
        return ResponseEntity.status(HttpStatus.OK).body(cidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> deleteById(@PathVariable Long id){
        cidadeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
