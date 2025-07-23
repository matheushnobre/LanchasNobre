package com.matheushnobre.LanchasNobre.exception;

public class FieldError {
    private String campo;
    private String erro;

    public FieldError(String field, String error) {
        this.campo = field;
        this.erro = error;
    }

    // Getters e Setters
    public String getCampo() { return campo; }
    public String getErro() { return erro; }
}