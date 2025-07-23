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
import java.util.Optional;

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
        lanchaValidator.validarInsercao(lancha);
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
        boolean existe = lanchaRepository.existsById(id);
        if(!existe){
            throw new RegistroNaoEncontradoException("Lancha com id " + id + " não encontrada.");
        }

        // Valida a alteração e persiste no banco.
        lanchaValidator.validarAtualizacao(id, lancha);
        lancha.setId(id);
        return lanchaRepository.save(lancha);
    }

    @Transactional
    public void deleteById(Long id){
        Optional<Lancha> lancha = lanchaRepository.findById(id);
        if(!lancha.isPresent()){
            throw new RegistroNaoEncontradoException("Lancha com id " + id + " não encontrada.");
        }

        lanchaValidator.validarRemocao(lancha.get());
        lanchaRepository.deleteById(id);
    }
}
