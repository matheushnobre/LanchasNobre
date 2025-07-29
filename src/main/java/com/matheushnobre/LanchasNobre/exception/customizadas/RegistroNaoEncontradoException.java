package com.matheushnobre.LanchasNobre.exception.customizadas;

public class RegistroNaoEncontradoException extends RuntimeException{
    public RegistroNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
