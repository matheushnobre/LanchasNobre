package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.exception.AtualizacaoInvalidaException;
import com.matheushnobre.LanchasNobre.exception.RegistroDuplicadoException;
import com.matheushnobre.LanchasNobre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioValidator {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validarInsercao(Usuario usuario) {
        if(existeComMesmoEmail(usuario) != -1) {
            throw new RegistroDuplicadoException("Usuário com mesmo e-mail já cadastrado.");
        }
    }

    public void validarAtualizacao(Long id, Usuario usuarioUpdate) {
        Long idExistente = existeComMesmoEmail(usuarioUpdate);
        if(idExistente != -1 && !idExistente.equals(id)) {
            throw new RegistroDuplicadoException("Usuário com mesmo e-mail já cadastrado.");
        }

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            if(isEmailAlterado(usuario.get(), usuarioUpdate)){
                throw new AtualizacaoInvalidaException("Não é permitido alterar o email do usuário.");
            }
        }
    }

    private Long existeComMesmoEmail(Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEncontrado.isPresent()) {
            return usuarioEncontrado.get().getId();
        }
        return -1L; // retorno caso não seja encontrado usuário com email dado
    }

    private boolean isEmailAlterado(Usuario usuario, Usuario usuarioUpdate){
        return !usuario.getEmail().equals(usuarioUpdate.getEmail());
    }
}
