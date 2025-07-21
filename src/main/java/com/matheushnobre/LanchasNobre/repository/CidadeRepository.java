package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    int countByNomeAndEstado(String nome, String estado);
}
