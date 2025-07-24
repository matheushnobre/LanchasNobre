package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassagemRepository extends JpaRepository<Passagem, Long> {
    Passagem findByAssentoAndViagem(Assento assento, Viagem viagem);
    List<Passagem> findByPassageiro(Usuario usuario);
    Integer countByViagem(Viagem viagem);
    List<Passagem> findByViagem(Viagem viagem);
}
