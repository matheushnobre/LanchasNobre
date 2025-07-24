package com.matheushnobre.LanchasNobre.dto;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import jakarta.validation.constraints.NotBlank;

public record CidadeDTO(
        @NotBlank(message = "Nome da cidade é obrigatório.")
        String nome,

        @NotBlank(message = "UF do estado é obrigatório.")
        String estado) {

    public Cidade toEntity(){
        Cidade cidade = new Cidade();
        cidade.setNome(nome);
        cidade.setEstado(estado);
        return cidade;
    }
}
