package com.matheushnobre.LanchasNobre.exception;

import java.util.List;

public class ApiError {
    private int status;
    private String mensagem;
    private List<FieldError> erros;

    public ApiError(int status, String message, List<FieldError> errors) {
        this.status = status;
        this.mensagem = message;
        this.erros = errors;
    }

    // Getters e Setters
    public int getStatus() { return status; }
    public String getMensagem() { return mensagem; }
    public List<FieldError> getErros() { return erros; }
}
