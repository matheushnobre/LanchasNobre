package com.matheushnobre.LanchasNobre.exception;

public class RegistroInvalidoException extends RuntimeException {
    public RegistroInvalidoException(String mensagem) {
        super(mensagem);
    }
}
