package com.matheushnobre.LanchasNobre.dto;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ViagemDTO(
        @NotNull(message = "Cidade de origem é obrigatório.")
        Cidade cidadeOrigem,

        @NotNull(message = "Cidade de destino é obrigatório.")
        Cidade cidadeDestino,

        @NotNull(message = "Data de partida é obrigatório.")
        @Future(message = "Data de partida não deve estar no passado.")
        LocalDateTime dataPartida,

        @NotNull(message = "Data de chegada é obrigatório.")
        @Future(message = "Data de chegada não deve estar no passado.")
        LocalDateTime dataChegada,

        @NotNull(message = "Lancha é obrigatório.")
        Lancha lancha,

        @NotNull(message = "Preço é obrigatório.")
        @Positive(message = "Preço deve ser um valor positivo.")
        Double preco) {

    public Viagem toEntity(){
        Viagem viagem = new Viagem();
        viagem.setDataPartida(dataPartida);
        viagem.setDataChegada(dataChegada);
        viagem.setCidadeOrigem(cidadeOrigem);
        viagem.setCidadeDestino(cidadeDestino);
        viagem.setLancha(lancha);
        viagem.setPreco(preco);
        return viagem;
    }
}
