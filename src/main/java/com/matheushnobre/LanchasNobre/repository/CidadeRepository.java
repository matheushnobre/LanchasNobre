package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNomeAndEstado(String nome, String estado);
}
