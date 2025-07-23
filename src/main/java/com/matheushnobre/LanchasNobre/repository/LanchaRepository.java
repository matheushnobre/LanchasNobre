package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.MapaInterno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanchaRepository extends JpaRepository<Lancha, Long> {
    Optional<Lancha> findByNome(String nome);

    List<Lancha> findByMapaInterno(MapaInterno mapaInterno);
}
