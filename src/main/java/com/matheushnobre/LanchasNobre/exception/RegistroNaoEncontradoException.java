package com.matheushnobre.LanchasNobre.exception;

public class RegistroNaoEncontradoException extends RuntimeException{
    public RegistroNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
