package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.dto.ViagemDTO;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.service.ViagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @PostMapping
    public ResponseEntity<Viagem> add(@Valid @RequestBody ViagemDTO viagemDTO) {
        Viagem viagem = viagemService.add(viagemDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(viagem);
    }

    @GetMapping
    public ResponseEntity<List<Viagem>> listAll() {
        List<Viagem> listaViagens = viagemService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(listaViagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viagem> getById(@PathVariable Long id) {
        Viagem viagem = viagemService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(viagem);
    }

    @GetMapping("/passageiros/{id}")
    public ResponseEntity<List<Usuario>> getPassageiros(@PathVariable Long id) {
        List<Usuario> listaPassageiros = viagemService.getPassageirosDaViagem(id);
        return ResponseEntity.status(HttpStatus.OK).body(listaPassageiros);
    }

    @GetMapping("/{idOrigem}/{idDestino}/{dataPartida}")
    public ResponseEntity<List<Viagem>> buscarComParametros(@PathVariable Long idOrigem, @PathVariable Long idDestino, @PathVariable LocalDate dataPartida){
        List<Viagem> lista = viagemService.buscarComParametros(idOrigem, idDestino, dataPartida);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viagem> updateById(@PathVariable Long id, @Valid @RequestBody ViagemDTO viagemDTO){
        Viagem viagem = viagemService.updateById(id, viagemDTO.toEntity());
        return ResponseEntity.status(HttpStatus.OK).body(viagem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        viagemService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
