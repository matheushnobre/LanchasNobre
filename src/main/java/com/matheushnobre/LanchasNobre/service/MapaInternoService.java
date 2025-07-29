package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Assento;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import com.matheushnobre.LanchasNobre.exception.customizadas.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import com.matheushnobre.LanchasNobre.validator.MapaInternoValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapaInternoService {

    @Autowired
    private MapaInternoRepository mapaInternoRepository;

    @Autowired
    MapaInternoValidator mapaInternoValidator;

    @Transactional
    public MapaInterno add(MapaInterno mapaInterno) {
        // Gera os assentos do mapa e, após isso, salva o mapa.
        gerarAssentos(mapaInterno);
        mapaInternoValidator.validarInsercao(mapaInterno);
        return mapaInternoRepository.save(mapaInterno);
    }

    public List<MapaInterno> listAll(){
        return mapaInternoRepository.findAll();
    }

    public MapaInterno getById(Long id){
        return mapaInternoRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Mapa com id " + id + " não encontrado")
        );
    }

    public void deleteById(Long id){
        Optional<MapaInterno> mapaInterno = mapaInternoRepository.findById(id);

        if(!mapaInterno.isPresent()){
            throw new RegistroNaoEncontradoException("Mapa com id " + id + " não encontrado");
        }

        mapaInternoValidator.validarRemocao(mapaInterno.get());
        mapaInternoRepository.deleteById(id);
    }

    private void gerarAssentos(MapaInterno mapaInterno) {
        // Mapear os assentos do mapa interno.
        // Código do assento será incrementado na ordem em que são lidos.

        List<Assento> assentos = new ArrayList<Assento>();
        String[] fileiras = mapaInterno.getDescricao().split("\n");

        int contador = 1;
        for(int x = 0; x < fileiras.length; x++) {
            String fileira = fileiras[x];
            for(int y = 0; y < fileira.length(); y++) {
                char c = fileira.charAt(y);
                if(c == 'P'){ // "P" representa uma poltrona, logo, irá criar este assento.
                    Assento assento = new Assento();
                    assento.setCodigoAssento(String.format("%03d", contador++));
                    assento.setMapaInterno(mapaInterno);
                    assento.setPosicaoX(x);
                    assento.setPosicaoY(y);
                    assentos.add(assento);
                }
            }
        }

        mapaInterno.setCapacidade(assentos.size());
        mapaInterno.setAssentos(assentos);
    }
}
