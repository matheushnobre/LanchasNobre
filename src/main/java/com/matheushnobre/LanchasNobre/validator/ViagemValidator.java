package com.matheushnobre.LanchasNobre.validator;

import com.matheushnobre.LanchasNobre.entity.StatusPagamento;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import com.matheushnobre.LanchasNobre.exception.RegistroUtilizadoException;
import com.matheushnobre.LanchasNobre.exception.ViagemNoPassadoException;
import com.matheushnobre.LanchasNobre.exception.RegistroInvalidoException;
import com.matheushnobre.LanchasNobre.exception.RegistroNaoEncontradoException;
import com.matheushnobre.LanchasNobre.repository.PassagemRepository;
import com.matheushnobre.LanchasNobre.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

@Component
public class ViagemValidator {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private PassagemRepository passagemRepository;

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
        // Se os IDs forem iguais, a viagem é inválida.
        return viagem.getCidadeOrigem().getId().equals(viagem.getCidadeDestino().getId());
    }

    private boolean isHorariosInvalidos(Viagem viagem){
        // Verifica se a data de partida é posterior a de chegada. Em caso positivo, os horários são inválidos.
        return viagem.getDataPartida().isAfter(viagem.getDataChegada());
    }

    private boolean isViagemNoPassado(Viagem viagem){
        // Verifica se a viagem já ocorreu comparando com o horário atual no fuso horário de Manaus.
        return viagem.getDataPartida().isBefore(ChronoLocalDateTime.from(ZonedDateTime.now(ZoneId.of("America/Manaus"))));
    }

    private boolean possuiPassagensVinculadas(Viagem viagem){
        return passagemRepository.countByViagem(viagem) > 0;
    }

    private boolean isLanchaAlterada(Viagem viagem, Viagem viagemUpdate){
        // A lancha é alterada se os IDs forem diferentes
        return !viagem.getLancha().getId().equals(viagemUpdate.getLancha().getId());
    }

    private boolean isCidadesAlteradas(Viagem viagem, Viagem viagemUpdate){
        // verifica se alguma das cidades está sendo atualizadda
        boolean origemAlterada = !viagem.getCidadeOrigem().getId().equals(viagemUpdate.getCidadeOrigem().getId());
        boolean destinoAlterado = !viagem.getCidadeDestino().getId().equals(viagemUpdate.getCidadeDestino().getId());
        return origemAlterada || destinoAlterado;
    }
}
