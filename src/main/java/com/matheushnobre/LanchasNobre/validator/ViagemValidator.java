package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.exception.RegistroUtilizadoException;
import com.matheushnobre.LanchasNobre.exception.ViagemNoPassadoException;
import com.matheushnobre.LanchasNobre.exception.RegistroInvalidoException;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.CidadeRepository;
import com.matheushnobre.LanchasNobre.repository.LanchaRepository;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;

@Component
public class ViagemValidator {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private PassagemRepository passagemRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private LanchaRepository lanchaRepository;

    public Viagem atualizarChavesEstrangeiras(Viagem viagem){
        Cidade cidadeOrigem = cidadeRepository.findById(viagem.getCidadeOrigem().getId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("Cidade com id " + viagem.getCidadeOrigem().getId() + " não encontrada.")
        );

        Cidade cidadeDestino = cidadeRepository.findById(viagem.getCidadeDestino().getId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("Cidade com id " + viagem.getCidadeDestino().getId() + " não encontrada.")
        );

        Lancha lancha = lanchaRepository.findById(viagem.getLancha().getId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("Lancha com id " + viagem.getLancha().getId() + " não encontrada.")
        );

        viagem.setCidadeOrigem(cidadeOrigem);
        viagem.setCidadeDestino(cidadeDestino);
        viagem.setLancha(lancha);

        return viagem;
    }

    public void validarInsercao(Viagem viagem) {
        if(isCidadesInvalidas(viagem)){
            throw new RegistroInvalidoException("Cidade de destino e de origem devem ser diferentes.");
        }

        if(isHorariosInvalidos(viagem)){
            throw new RegistroInvalidoException("Horário de chegada deve ser posterior ao horário de partida.");
        }
    }

    public void validarAtualizacao(Long id, Viagem viagemUpdate) {
        Viagem viagem = viagemRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Viagem com id " + id + " não encontrada.")
        );

        boolean possuiPassagens = possuiPassagensVinculadas(viagem);

        if(isViagemNoPassado(viagem)){
            throw new ViagemNoPassadoException("Não é permitido atualizar viagens que já ocorreram/estão ocorrendo.");
        }

        if(isCidadesAlteradas(viagem, viagemUpdate)){
            if(possuiPassagens){
                throw new RegistroUtilizadoException("Viagem já possui passagens vinculadas, logo não é possível alterar cidade de origem e/ou destino.");
            }

            if(isCidadesInvalidas(viagemUpdate)){
                throw new RegistroInvalidoException("Cidade de destino e de origem devem ser diferentes.");
            }
        }

        if(isHorariosInvalidos(viagemUpdate)){
            throw new RegistroInvalidoException("Horário de chegada deve ser posterior ao horário de partida.");
        }

        if(isLanchaAlterada(viagem, viagemUpdate)) {
            if (possuiPassagens) {
                throw new RegistroUtilizadoException("Viagem já possui passagens vinculadas, logo não é possível alterar a lancha vinculada.");
            }
        }
    }

    public void validarRemocao(Long id){
        Viagem viagem = viagemRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("Viagem com id " + id + " não encontrada")
        );

        if(possuiPassagensVinculadas(viagem)){
            throw new RegistroUtilizadoException("Viagem possui passagens vinculadas.");
        }

    }

    private boolean isCidadesInvalidas(Viagem viagem){
        return viagem.getCidadeOrigem().getId().equals(viagem.getCidadeDestino().getId());
    }

    private boolean isHorariosInvalidos(Viagem viagem){
        return viagem.getDataPartida().isAfter(viagem.getDataChegada());
    }

    private boolean isViagemNoPassado(Viagem viagem){
        return viagem.getDataPartida().isBefore(ChronoLocalDateTime.from(ZonedDateTime.now(ZoneId.of("America/Manaus"))));
    }

    private boolean possuiPassagensVinculadas(Viagem viagem){
        return passagemRepository.countByViagem(viagem) > 0;
    }

    private boolean isLanchaAlterada(Viagem viagem, Viagem viagemUpdate){
        // A lancha é modificada se os IDs forem diferentes. Isso signfica que o cliente está tentando alterar este campo.
        return !viagem.getLancha().getId().equals(viagemUpdate.getLancha().getId());
    }

    private boolean isCidadesAlteradas(Viagem viagem, Viagem viagemUpdate){
        // Verifica se alguma das cidades está sendo atualizada pelo cliente.
        boolean origemAlterada = !viagem.getCidadeOrigem().getId().equals(viagemUpdate.getCidadeOrigem().getId());
        boolean destinoAlterado = !viagem.getCidadeDestino().getId().equals(viagemUpdate.getCidadeDestino().getId());
        return origemAlterada || destinoAlterado;
    }
}
