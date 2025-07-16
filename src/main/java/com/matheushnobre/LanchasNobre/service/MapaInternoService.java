package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Assento;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapaInternoService {
    @Autowired
    private MapaInternoRepository mapaInternoRepository;

    public MapaInterno criarMapaInternoComAssentos(MapaInterno mapaInterno) {
        // Mapear os assentos do mapa interno, criando-os e persirstindo-os no banco.
        // Código do assento será incrementado na ordem em que são lidos.

        List<Assento> assentos = new ArrayList<Assento>();
        String[] fileiras = mapaInterno.getDescricao().split("\n");

        int contador = 1;
        for(int x = 0; x < fileiras.length; x++) {
            String fileira = fileiras[x];
            for(int y = 0; y < fileira.length(); y++) {
                char c = fileira.charAt(y);
                if(c == 'P'){
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

        return mapaInternoRepository.save(mapaInterno);
    }

    public List<MapaInterno> listarTodos(){
        return mapaInternoRepository.findAll();
    }

    public boolean deletarPorId(Long id){
        // Busca o mapa interno com o id solicitado e,
        // caso exista, deleta-o. Caso contrário, reporta isto.

        if(mapaInternoRepository.existsById(id)){
            mapaInternoRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
