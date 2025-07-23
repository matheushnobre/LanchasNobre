package com.matheushnobre.LanchasNobre.controller;

import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> adicionar(@RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> lista = usuarioService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> selecionarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.selecionarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @GetMapping("/listarPassagens/{id}")
    public ResponseEntity<List<Passagem>> listarPassagensDoUsuario(@PathVariable Long id) {
        List<Passagem> lista = usuarioService.listarPassagensDoUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        boolean foiDeletado = usuarioService.deletar(id);
        if(foiDeletado) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
