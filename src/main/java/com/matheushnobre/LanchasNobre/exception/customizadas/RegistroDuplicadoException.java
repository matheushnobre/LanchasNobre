package com.matheushnobre.LanchasNobre.exception.customizadas;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String mensagem){
        super(mensagem);
    }
}
