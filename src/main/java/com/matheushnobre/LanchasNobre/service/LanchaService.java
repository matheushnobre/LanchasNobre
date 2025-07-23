package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.LanchaRepository;
import com.matheushnobre.LanchasNobre.repository.MapaInternoRepository;
import com.matheushnobre.LanchasNobre.validator.LanchaValidator;
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

    @Autowired
    private LanchaValidator lanchaValidator;

    @Transactional
    public Lancha add(Lancha lancha){
        lanchaValidator.validar(lancha);
        return lanchaRepository.save(lancha);
    }

    public List<Lancha> listAll(){
        return lanchaRepository.findAll();
    }

    public Lancha getById(Long id){
        return lanchaRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Lancha com id " + id + " não encontrada.")
        );
    }

    @Transactional
    public Lancha updateById(Long id, Lancha lancha){
        // Busca por lancha com id solicitado.
        Lancha lanchaAtual = lanchaRepository.findById(id).orElseThrow
                (() -> new RegistroNaoEncontradoException("Lancha com id " + id + " não encontrada."));

        // Atualizando dados localmente.
        lanchaAtual.setNome(lancha.getNome());
        lanchaAtual.setAnoFabricacao(lancha.getAnoFabricacao());
        lanchaAtual.setMapaInterno(lancha.getMapaInterno());

        // Valida a alteração e persiste no banco.
        lanchaValidator.validar(lanchaAtual);
        return lanchaRepository.save(lanchaAtual);
    }

    @Transactional
    public void deleteById(Long id){
        if(!lanchaRepository.existsById(id)){
            throw new RegistroNaoEncontradoException("Lancha com id " + id + " não encontrada.");
        }

        lanchaRepository.deleteById(id);
    }
}
