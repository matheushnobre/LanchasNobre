package com.matheushnobre.LanchasNobre.exception.customizadas;

public class ViagemNoPassadoException extends RuntimeException {
    public ViagemNoPassadoException(String mensagem) {
        super(mensagem);
    }
}
