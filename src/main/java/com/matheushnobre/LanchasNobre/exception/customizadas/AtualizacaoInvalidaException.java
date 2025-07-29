package com.matheushnobre.LanchasNobre.exception.customizadas;

public class AtualizacaoInvalidaException extends RuntimeException {
    public AtualizacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
