package com.matheushnobre.LanchasNobre.entity;

public enum Status {
    PROCESSAMENTO("Em processamento"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
