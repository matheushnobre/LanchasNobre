package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import com.matheushnobre.LanchasNobre.exception.RecursoDuplicadoException;
import com.matheushnobre.LanchasNobre.exception.RecursoNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.LanchaRepository;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanchaService {

    @Autowired
    private LanchaRepository lanchaRepository;

    @Autowired
    private MapaInternoRepository mapaInternoRepository;

    @Transactional
    public Lancha salvar(Lancha lancha){
        // Para criar uma lancha, a mesma deve atender alguns critérios, expostos em validarLancha()
        validarLancha(lancha);
        return lanchaRepository.save(lancha);
    }

    public List<Lancha> listarTodas(){
        return lanchaRepository.findAll();
    }

    @Transactional
    public Lancha atualizar(Long id, Lancha lancha){
        // Para atualizar uma lancha, iremos realizar a validação e depois persistir esta atualização no banco.

        // busca por lancha com id solicitado
        Lancha lanchaAtualizada = lanchaRepository.findById(id).orElseThrow
                (() -> new RecursoNaoEncontradoException("Lancha com ID " + id + " nao encontrada."));

        // atualiza campos desejados
        if(lancha.getNome() != null) lanchaAtualizada.setNome(lancha.getNome());
        if(lancha.getMapaInterno() != null) lanchaAtualizada.setMapaInterno(lancha.getMapaInterno());
        if(lancha.getAno_fabricacao() != null) lanchaAtualizada.setAno_fabricacao(lancha.getAno_fabricacao());

        // valida e persiste no banco
        validarLancha(lanchaAtualizada);
        return lanchaRepository.save(lanchaAtualizada);
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

    private boolean validarLancha(Lancha lancha){
        // 1. Nome deve ser único, ou seja, não deve possuir outra lancha com mesmo nome (tratado no banco).
        // 2. O id do mapa interno deve ser válido.

        MapaInterno mapa = mapaInternoRepository.findById(lancha.getMapaInterno().getId()).orElseThrow
                (() -> new RecursoNaoEncontradoException("MapaInterno nao encontrado."));
        lancha.setMapaInterno(mapa);

        return true;
    }
}
