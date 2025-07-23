package com.matheushnobre.LanchasNobre.dto;

import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import jakarta.validation.constraints.NotBlank;

public record MapaInternoDTO(
        @NotBlank(message = "Descrição é um campo obrigatório.")
        String descricao) {

        public MapaInterno toEntity() {
                MapaInterno mapaInterno = new MapaInterno();
                mapaInterno.setDescricao(descricao);
                return mapaInterno;
        }
}
