package com.matheushnobre.LanchasNobre.dto;

import com.matheushnobre.LanchasNobre.entity.Assento;
import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import jakarta.validation.constraints.NotNull;

public record PassagemDTO(
        @NotNull(message = "Viagem é um campo obrigatório.")
        Viagem viagem,

        @NotNull(message = "Assento é um campo obrigatório")
        Assento assento,

        @NotNull(message = "Passageiro é um campo obrigatório.")
        Usuario passageiro) {

    public Passagem toEntity(){
        Passagem passagem = new Passagem();
        passagem.setAssento(assento);
        passagem.setPassageiro(passageiro);
        passagem.setViagem(viagem);
        return passagem;
    }
}
