package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.*;
import com.matheushnobre.LanchasNobre.exception.customizadas.AssentoOcupadoException;
import com.matheushnobre.LanchasNobre.exception.customizadas.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.exception.customizadas.ViagemNoPassadoException;
import com.matheushnobre.LanchasNobre.repository.AssentoRepository;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.repository.UsuarioRepository;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PassagemValidator {

    @Autowired
    PassagemRepository passagemRepository;

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private AssentoRepository assentoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Passagem atualizarChavesEstrangeiras(Passagem passagem) {
        Viagem viagem = viagemRepository.findById(passagem.getViagem().getId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("Viagem não encontradada.")
        );

        Assento assento = assentoRepository.findById(passagem.getAssento().getId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("Assento não encontrado.")
        );

        Usuario passageiro = usuarioRepository.findById(passagem.getPassageiro().getId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("Passageiro não encontrado.")
        );

        passagem.setViagem(viagem);
        passagem.setAssento(assento);
        passagem.setPassageiro(passageiro);
        return passagem;
    }

    public void validarInsercao(Passagem passagem) {
        Passagem p = passagemRepository.findByAssentoAndViagem(passagem.getAssento(), passagem.getViagem());
        if(isAssentoocupado(passagem)) {
            throw new AssentoOcupadoException("Assento já ocupado.");
        }

        if(isViagemNoPassado(passagem)) {
            throw new ViagemNoPassadoException("Não é possível vender passagens para viagens que já ocorreram/estão ocorrendo.");
        }
    }

    private boolean isAssentoocupado(Passagem passagem){
        Passagem p = passagemRepository.findByAssentoAndViagem(passagem.getAssento(), passagem.getViagem());
        return(p != null && p.getStatusPagamento() != StatusPagamento.CANCELADA);
    }

    private boolean isViagemNoPassado(Passagem passagem){
        return passagem.getViagem().getDataPartida().isBefore(LocalDateTime.now());
    }
}
