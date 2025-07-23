package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapaInternoRepository extends JpaRepository<MapaInterno, Long>{
    Optional<MapaInterno> findByDescricao(String descricao);
}
