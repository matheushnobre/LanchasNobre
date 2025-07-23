package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.exception.RegistroDuplicadoException;
import com.matheushnobre.LanchasNobre.repository.LanchaRepository;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LanchaValidator {

    @Autowired
    private LanchaRepository lanchaRepository;

    @Autowired
    MapaInternoRepository mapaInternoRepository;

    public void validar(Lancha lancha) {
        if(existsByNome(lancha)){
            throw new RegistroDuplicadoException("Nome de lancha já utilizado.");
        }
    }

    private boolean existsByNome(Lancha lancha) {
        System.out.println("validar nome");
        Optional<Lancha> lanchaEncontrada = lanchaRepository.findByNome(lancha.getNome());

        // Se o ID da lancha é nulo, significa que eu estou cadastrando esta lancha pela primeira vez.
        if(lancha.getId() == null){
            return lanchaEncontrada.isPresent(); // Retorna true caso já exista autor com este nome, false caso contrário.
        }

        // Se o ID da lancha não for nulo, quer dizer que esta lancha já está salva no banco.
        // Neste caso, pode tratar-se de uma validação para ação de atualização, sendo necessário validar se o nome encontrado já pertence a esta lancha.
        if(lanchaEncontrada.isPresent()){
            System.out.println(lanchaEncontrada.get().getNome() + " " + lancha.getNome());
            return !lanchaEncontrada.get().getNome().equals(lancha.getNome());
        }

        return false;
    }

}
