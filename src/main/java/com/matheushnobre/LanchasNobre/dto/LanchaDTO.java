package com.matheushnobre.LanchasNobre.dto;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LanchaDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotNull(message = "Ano de fabricação é obrigatório.")
        @Min(value = 1900, message = "Ano inválido.")
        Integer anoFabricacao,

        @NotNull(message = "ID do mapa é obrigatório.")
        MapaInterno mapaInterno) {

    public Lancha toEntity(){
        Lancha lancha = new Lancha();
        lancha.setNome(nome);
        lancha.setAnoFabricacao(anoFabricacao);
        lancha.setMapaInterno(mapaInterno);
        return lancha;
    }

}
