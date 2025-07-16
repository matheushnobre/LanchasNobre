package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import com.matheushnobre.LanchasNobre.service.MapaInternoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mapa_interno")
public class MapaInternoController {
    @Autowired
    private MapaInternoService mapaInternoService;

    @PostMapping
    public ResponseEntity<MapaInterno> criar(@RequestBody MapaInterno mapaInterno) {
        MapaInterno salvo = mapaInternoService.criarMapaInternoComAssentos(mapaInterno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<MapaInterno>> listarTodos(){
        List<MapaInterno> lista = mapaInternoService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MapaInterno> deletarPorId(@PathVariable Long id) {
        boolean deletado = mapaInternoService.deletarPorId(id);
        if(deletado) {
            return ResponseEntity.noContent().build(); // 204 noContent (deletado)
        }
        return ResponseEntity.notFound().build(); // 404 notFound
    }
}
