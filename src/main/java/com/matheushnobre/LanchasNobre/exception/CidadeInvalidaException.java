package com.matheushnobre.LanchasNobre.exception;

public class CidadeInvalidaException extends RuntimeException{
    public CidadeInvalidaException(String mensagem){
        super(mensagem);
    }
}
