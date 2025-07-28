package com.matheushnobre.LanchasNobre.dto;

import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.util.CPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UsuarioDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotNull(message = "Data de nascimento é obrigatório.")
        @Past(message = "Data de nascimento deve estar no passado.")
        LocalDate dataNascimento,

        @NotNull(message = "CPF é obrigatório.")
        @CPF(message = "CPF inválido.")
        String cpf) {

        public Usuario toEntity(){
                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setDataNascimento(dataNascimento);
                usuario.setCpf(cpf);
                return usuario;
        }
}
