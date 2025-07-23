package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.dto.MapaInternoDTO;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import com.matheushnobre.LanchasNobre.service.MapaInternoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<MapaInterno> add(@RequestBody @Valid MapaInternoDTO mapaInternoDTO) {
        MapaInterno mapaCriado = mapaInternoService.add(mapaInternoDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapaCriado);
    }

    @GetMapping
    public ResponseEntity<List<MapaInterno>> listAll(){
        List<MapaInterno> lista = mapaInternoService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MapaInterno> getById(@PathVariable Long id){
        MapaInterno mapa = mapaInternoService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapa);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<MapaInterno> deleteById(@PathVariable Long id) {
        mapaInternoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
