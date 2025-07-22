package com.matheushnobre.LanchasNobre.entity;

public enum StatusPagamento {
    PROCESSAMENTO("Em processamento"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
