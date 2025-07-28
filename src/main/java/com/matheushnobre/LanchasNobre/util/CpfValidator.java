package com.matheushnobre.LanchasNobre.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CPF, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context){
        if(cpf == null || cpf.length() != 11) return false;

        int soma1 = 0, soma2 = 0;
        for(int i=0; i<9; i++){
            int atual = cpf.charAt(i) - '0';
            soma1 += atual * (10-i);
            soma2 += atual * (11-i);
        }

        // cálculo do primeiro dígito verificador
        int resto = soma1 % 11;
        if(!verificaDigito(cpf, resto, 9)) return false;

        // cálculo do segundo dígito verificador
        soma2 += (cpf.charAt(9) - '0') * 2;
        resto = soma2 % 11;
        return (verificaDigito(cpf, resto, 10));
    }

    private boolean verificaDigito(String cpf, int resto, int posicao){
        if(resto == 0 || resto == 1){
            if(cpf.charAt(posicao) != '0') return false;
        } else{
            int digitoEsperado = 11 - resto;
            if(cpf.charAt(posicao) - '0' != digitoEsperado) return false;
        }
        return true;
    }
}
