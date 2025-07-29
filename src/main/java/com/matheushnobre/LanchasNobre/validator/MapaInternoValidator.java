package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import com.matheushnobre.LanchasNobre.exception.customizadas.RegistroDuplicadoException;
import com.matheushnobre.LanchasNobre.exception.customizadas.RegistroInvalidoException;
import com.matheushnobre.LanchasNobre.exception.customizadas.RegistroUtilizadoException;
import com.matheushnobre.LanchasNobre.repository.LanchaRepository;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MapaInternoValidator {

    @Autowired
    private MapaInternoRepository mapaInternoRepository;

    @Autowired
    private LanchaRepository lanchaRepository;

    public void validarInsercao(MapaInterno mapaInterno) {
        if(existeMapa(mapaInterno)){
            throw new RegistroDuplicadoException("Mapa interno com a mesma descrição já cadastrado.");
        }

        if(!isCapacidadePositiva(mapaInterno)){
            throw new RegistroInvalidoException("Mapa interno deve possuir capacidade positiva.");
        }
    }

    public void validarRemocao(MapaInterno mapaInterno) {
        if(isUtilizado(mapaInterno)){
            throw new RegistroUtilizadoException("Mapa está sendo utilizado em outros registros.");
        }
    }

    private boolean existeMapa(MapaInterno mapaInterno) {
        Optional<MapaInterno> mapa = mapaInternoRepository.findByDescricao(mapaInterno.getDescricao());

        if(mapaInterno.getId() == null){
            return mapa.isPresent(); // Se o ID for nulo, é porque ainda estamos criando o mapa. Logo, se existir mapa com a mesma descrição, é porque este já está presente no sistema.
        }

        if(mapa.isPresent()) return false;
        return !mapa.get().getId().equals(mapaInterno.getId());
    }

    private boolean isCapacidadePositiva(MapaInterno mapaInterno) {
        return mapaInterno.getCapacidade() > 0;
    }

    private boolean isUtilizado(MapaInterno mapaInterno) {
        List<Lancha> lanchas = lanchaRepository.findByMapaInterno(mapaInterno);
        return !lanchas.isEmpty();
    }
}
