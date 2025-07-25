package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.exception.RegistroDuplicadoException;
import com.matheushnobre.LanchasNobre.exception.RegistroUtilizadoException;
import com.matheushnobre.LanchasNobre.repository.CidadeRepository;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CidadeValidator {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ViagemRepository viagemRepository;

    public void validarInsercao(Cidade cidade){
        if(existeIgual(cidade) != -1L){
            throw new RegistroDuplicadoException("Cidade já cadastrada.");
        }
    }

    public void validarAtualizacao(Long id, Cidade cidade){
        Long idEncontrado = existeIgual(cidade);
        if(idEncontrado != -1L && !idEncontrado.equals(id)){
            throw new RegistroDuplicadoException("Cidade já cadastrada.");
        }
    }

    public void validarRemocao(Cidade cidade){
        if(isUtilizada(cidade)){
            throw new RegistroUtilizadoException("Cidade está sendo utilizada em outros registros.");
        }
    }

    private Long existeIgual(Cidade cidade){
        Optional<Cidade> cidadeEncontrada = cidadeRepository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
        if(cidadeEncontrada.isPresent()){
            return cidadeEncontrada.get().getId();
        }
        return -1L; // Retorno para caso não possua outra cidade igual já cadastrada no sistema.
    }

    private boolean isUtilizada(Cidade cidade){
        // Verifica se a cidade é utilizada em algum registro de viagem, seja como cidade de partida ou cidade de chegada.
        return (viagemRepository.countByCidadeOrigem(cidade) > 0 || viagemRepository.countByCidadeDestino(cidade) > 0);
    }
}
