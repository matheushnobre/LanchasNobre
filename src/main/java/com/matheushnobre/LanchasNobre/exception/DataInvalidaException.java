package com.matheushnobre.LanchasNobre.exception;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String mensagem) {
        super(mensagem);
    }
}
