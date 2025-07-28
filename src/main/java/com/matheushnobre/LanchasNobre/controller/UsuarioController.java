package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.dto.UsuarioDTO;
import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> add(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioCriado = usuarioService.add(usuarioDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listAll() {
        List<Usuario> lista = usuarioService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @GetMapping("/listarPassagens/{id}")
    public ResponseEntity<List<Passagem>> getPassagensUsuario(@PathVariable Long id) {
        List<Passagem> lista = usuarioService.getPassagensUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateById(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioAtualizado = usuarioService.updateById(id, usuarioDTO.toEntity());
        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        usuarioService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
