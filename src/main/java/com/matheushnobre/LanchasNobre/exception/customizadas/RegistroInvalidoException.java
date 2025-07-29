package com.matheushnobre.LanchasNobre.exception.customizadas;

public class RegistroInvalidoException extends RuntimeException {
    public RegistroInvalidoException(String mensagem) {
        super(mensagem);
    }
}
