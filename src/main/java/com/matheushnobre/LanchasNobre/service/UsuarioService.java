package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.exception.customizadas.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.repository.UsuarioRepository;
import com.matheushnobre.LanchasNobre.validator.UsuarioValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PassagemRepository passagemRepository;

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Transactional
    public Usuario add(Usuario usuario) {
        usuarioValidator.validarInsercao(usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getById(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Usuário com id solicitado não encontrado")
        );
    }

    public List<Passagem> getPassagensUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Usuário com id solicitado não encontrado.")
        );
        return passagemRepository.findByPassageiro(usuario);
    }

    @Transactional
    public Usuario updateById(Long id, Usuario usuario) {
        // Procura pelo usuario que será atualizado
        boolean existe = usuarioRepository.existsById(id);
        if(!existe){
            throw new RegistroNaoEncontradoException("Usuário com id " + id + " não encontrado.");
        }

        usuarioValidator.validarAtualizacao(id, usuario);
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Usuário com id " + id + " não encontrado")
        );
        usuarioValidator.validarRemocao(usuario);
        usuarioRepository.delete(usuario);
    }
}
