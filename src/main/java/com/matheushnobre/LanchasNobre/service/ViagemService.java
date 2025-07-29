package com.matheushnobre.LanchasNobre.service;

import com.matheushnobre.LanchasNobre.entity.*;
import com.matheushnobre.LanchasNobre.exception.customizadas.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.CidadeRepository;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import com.matheushnobre.LanchasNobre.validator.ViagemValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    PassagemRepository passagemRepository;

    @Autowired
    ViagemValidator viagemValidator;

    @Autowired
    CidadeRepository cidadeRepository;

    @Transactional
    public Viagem add(Viagem viagem){
        viagem = viagemValidator.atualizarChavesEstrangeiras(viagem); // Devido ao fato do cliente passar somente o ID, eu atualizo as chaves estrangeiras para recuperar todas as informações das
        // cidades de origem e destino, além da lancha. Isso me auxilia a retornar todos os dados para o cliente posteriormente.
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

        // Filtro apenas os passageiros que estão vinculados a esta viagem e os retorno
        return passagemRepository.findByViagem(viagem)
                .stream()
                .map(Passagem::getPassageiro)
                .toList();
    }

    public List<Viagem> buscarComParametros(Long idOrigem, Long idDestino, LocalDate dataPartida){
        Optional<Cidade> origem = cidadeRepository.findById(idOrigem);
        Optional<Cidade> destino = cidadeRepository.findById(idDestino);

        if(origem.isEmpty() || destino.isEmpty()){
            return new ArrayList<>(); // retorna lista vazia porque tais cidades são inválidas, logo não há rotas disponíveis.
        }

        // Filtro do dia, a consulta será por viagens que respeitem o horário entre 0:00 até 23:59 do dia solicitado.
        LocalDateTime inicioDia = dataPartida.atStartOfDay();
        LocalDateTime fimDia = dataPartida.atTime(23, 59, 59);

        return viagemRepository.findByCidadeOrigemAndCidadeDestinoAndDataPartidaBetween(
                origem.get(), destino.get(), inicioDia, fimDia);
    }

    @Transactional
    public Viagem updateById(Long id, Viagem viagem){
        boolean existe = viagemRepository.existsById(id);
        if(!existe){
            throw new RegistroNaoEncontradoException("Viagem com id " + id + " não encontrada.");
        }

        viagem = viagemValidator.atualizarChavesEstrangeiras(viagem); // Mesma lógica do add, atualizo as chaves estrangeiras para melhor retorno ao cliente posteriormente.
        viagemValidator.validarAtualizacao(id, viagem);
        viagem.setId(id);
        return viagemRepository.save(viagem);
    }

    public void deleteById(Long id){
        viagemValidator.validarRemocao(id);
        viagemRepository.deleteById(id);
    }
}
