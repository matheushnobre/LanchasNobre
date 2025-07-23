package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.repository.UsuarioRepository;
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

    @Transactional
    public Usuario salvar(Usuario usuario) {
        // implementar validação posteriormente (somente cpf)
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario selecionarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Usuário com id solicitado não encontrado")
        );
    }

    public List<Passagem> listarPassagensDoUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Usuário com id solicitado não encontrado.")
        );
        return passagemRepository.findByPassageiro(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuario) {
        // Procura pelo usuario que será atualizado
        Usuario usuarioAtualizado = usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Usuário não encontrado.")
        );

        if(usuario.getNome() != null) usuarioAtualizado.setNome(usuario.getNome());
        if(usuario.getCpf() != null) usuarioAtualizado.setCpf(usuario.getCpf());
        if(usuario.getDataNascimento() != null) usuarioAtualizado.setDataNascimento(usuario.getDataNascimento());
        if(usuario.getCpf() != null) usuarioAtualizado.setCpf(usuario.getCpf());

        // implementar validação posteriormente (somente cpf)
        return usuarioRepository.save(usuarioAtualizado);
    }

    public boolean deletar(Long id) {
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
