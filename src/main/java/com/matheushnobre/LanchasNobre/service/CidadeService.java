package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.CidadeRepository;
import com.matheushnobre.LanchasNobre.validator.CidadeValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeValidator cidadeValidator;

    @Transactional
    public Cidade add(Cidade cidade) {
        cidadeValidator.validarInsercao(cidade);
        return cidadeRepository.save(cidade);
    }

    public List<Cidade> listAll(){
        return cidadeRepository.findAll();
    }

    @Transactional
    public Cidade updateById(Long id, Cidade cidade) {
        boolean existe = cidadeRepository.existsById(id);
        if(!existe){
            throw new RegistroNaoEncontradoException("Cidade com id " + id + " não encontrada.");
        }

        cidadeValidator.validarAtualizacao(id, cidade);
        cidade.setId(id);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void deleteById(Long id) {
        Cidade cidade = cidadeRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Cidade com id " + id + " não encontrada.")
        );

        cidadeValidator.validarRemocao(cidade);
        cidadeRepository.deleteById(id);
    }
}
