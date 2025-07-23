package com.matheushnobre.LanchasNobre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Erros de validação.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de Validação", fieldErrors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    // Erro de registro duplicado (conflito).
    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ApiError> handleRegistroDuplicado(RegistroDuplicadoException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    // Erro de registro não encontrado.
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ApiError> handleRegistroNaoEncontrado(RegistroNaoEncontradoException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    // Erro de registro não inválido.
    @ExceptionHandler(RegistroInvalidoException.class)
    public ResponseEntity<ApiError> handleRegistroInvalido(RegistroInvalidoException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}
