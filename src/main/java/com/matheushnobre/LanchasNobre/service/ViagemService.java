package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import com.matheushnobre.LanchasNobre.validator.ViagemValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    PassagemRepository passagemRepository;

    @Autowired
    ViagemValidator viagemValidator;

    @Transactional
    public Viagem add(Viagem viagem){
        viagemValidator.validarInsercao(viagem);
        return viagemRepository.save(viagem);
    }

    public List<Viagem> listAll(){
        return viagemRepository.findAll();
    }

    public Viagem getById(Long id){
        Viagem viagem = viagemRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Viagem com id " + id + " não encontrada")
        );
        return viagem;
    }

    public List<Usuario> getPassageirosDaViagem(Long id) {
        Viagem viagem = viagemRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Viagem com id " + id + " não encontrada.")
        );

        return passagemRepository.findByViagem(viagem)
                .stream()
                .map(Passagem::getPassageiro)
                .toList();
    }

    @Transactional
    public Viagem updateById(Long id, Viagem viagem){
        // Valida e persiste a atualização
        viagemValidator.validarAtualizacao(id, viagem);
        viagem.setId(id);
        return viagemRepository.save(viagem);
    }

    public void deleteById(Long id){
        viagemValidator.validarRemocao(id);
        viagemRepository.deleteById(id);
    }
}
