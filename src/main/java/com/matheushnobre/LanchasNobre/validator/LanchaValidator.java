package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.exception.RegistroDuplicadoException;
import com.matheushnobre.LanchasNobre.exception.RegistroUtilizadoException;
import com.matheushnobre.LanchasNobre.repository.LanchaRepository;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LanchaValidator {

    @Autowired
    private LanchaRepository lanchaRepository;

    @Autowired
    ViagemRepository viagemRepository;

    public void validarInsercao(Lancha lancha) {
        if(existsByNome(lancha) != -1L){
            throw new RegistroDuplicadoException("Nome de lancha já utilizado.");
        }
    }

    public void validarAtualizacao(Long id, Lancha lancha) {
        Long idExistente = existsByNome(lancha);
        if(idExistente != -1L && !idExistente.equals(id)){
            throw new RegistroDuplicadoException("Nome de lancha já utilizado.");
        }
    }

    public void validarRemocao(Lancha lancha){
        if(isUtilizada(lancha)){
            throw new RegistroUtilizadoException("Lancha está sendo utilizada em outros registros");
        }
    }

    private Long existsByNome(Lancha lancha) {
        Optional<Lancha> lanchaEncontrada = lanchaRepository.findByNome(lancha.getNome());

        if(lanchaEncontrada.isPresent()){
            return lanchaEncontrada.get().getId();
        }

        return -1L; // retorno caso a cidade não esteja cadastrada no banco de dados.
    }

    private boolean isUtilizada(Lancha lancha) {
        List<Viagem> viagens = viagemRepository.findByLancha(lancha);
        return !viagens.isEmpty();
    }

}
