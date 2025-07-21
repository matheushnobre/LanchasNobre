package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.exception.RecursoDuplicadoException;
import com.matheushnobre.LanchasNobre.repository.CidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Transactional
    public Cidade salvar(Cidade cidade) {
        // Para salvar uma cidade, devemos verificar primeiro se já não existe outra
        // cidade salva com as mesmas informações

        if(cidadeRepository.countByNomeAndEstado(cidade.getNome(), cidade.getEstado()) >= 1){
            throw new RecursoDuplicadoException("Cidade já cadastrada no sistema.");
        }

        return cidadeRepository.save(cidade);
    }

    public List<Cidade> listarTodas(){
        return cidadeRepository.findAll();
    }

}
