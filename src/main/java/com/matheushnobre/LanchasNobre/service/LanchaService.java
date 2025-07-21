package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import com.matheushnobre.LanchasNobre.exception.RecursoDuplicadoException;
import com.matheushnobre.LanchasNobre.exception.RecursoNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.LanchaRepository;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanchaService {

    @Autowired
    private LanchaRepository lanchaRepository;

    @Autowired
    private MapaInternoRepository mapaInternoRepository;

    public Lancha salvar(Lancha lancha){
        // Para criar uma lancha, a mesma deve atender alguns critérios:
        // 1. Nome deve ser único, ou seja, não deve possuir outra lancha com mesmo nome.
        // 2. O id do mapa interno deve ser válido.

        if(lanchaRepository.existsByNome(lancha.getNome())){
            throw new RecursoDuplicadoException("Já existe uma lancha com este nome.");
        }

        Long mapaId = lancha.getMapaInterno().getId();
        MapaInterno mapa = mapaInternoRepository.findById(mapaId).orElseThrow
                (() -> new RecursoNaoEncontradoException("MapaInterno com ID " + mapaId + " nao encontrado."));
        lancha.setMapaInterno(mapa);

        return lanchaRepository.save(lancha);
    }

    public List<Lancha> listarTodas(){
        return lanchaRepository.findAll();
    }

    public Lancha atualizar(Long id, Lancha dados){
        // Para atualizar uma lancha, iremos verificar a lancha já existente
        // e editar somente os dados desejados pelo usuário. Segue a mesma regra da criação,
        // ou seja, nome não deve ser repetido e id_mapa_interno deve ser válido.

        Lancha lanchaExistente = lanchaRepository.findById(id).orElseThrow
                (() -> new RecursoNaoEncontradoException("Lancha com ID " + id + " nao encontrada."));

        if(dados.getNome() != null){
            // Verifica se o novo nome já pertence a outra lancha (diferente da atual)
            if(!lanchaExistente.getNome().equals(dados.getNome()) && lanchaRepository.existsByNome(dados.getNome())){
                throw new RecursoDuplicadoException("Já existe lancha com este nome.");
            }
            lanchaExistente.setNome(dados.getNome());
        }

        if(dados.getMapaInterno() != null){
            Long mapaId = dados.getMapaInterno().getId();
            MapaInterno mapa = mapaInternoRepository.findById(mapaId).orElseThrow
                    (() -> new RecursoNaoEncontradoException("MapaInterno com ID " + mapaId + " nao encontrado."));
            lanchaExistente.setMapaInterno(mapa);
        }

        if(dados.getAno_fabricacao() != null){
            lanchaExistente.setAno_fabricacao(dados.getAno_fabricacao());
        }

        return lanchaRepository.save(lanchaExistente);
    }

    public boolean deletarPorId(Long id){
        // Busca a lancha com o id solicitado e,
        // caso exista, deleta-a. Caso contrário, reporta isto.

        if(lanchaRepository.existsById(id)){
            lanchaRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
