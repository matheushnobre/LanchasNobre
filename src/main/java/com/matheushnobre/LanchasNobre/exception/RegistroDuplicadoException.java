package com.matheushnobre.LanchasNobre.exception;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String mensagem){
        super(mensagem);
    }
}
