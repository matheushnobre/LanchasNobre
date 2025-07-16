package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.Lancha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanchaRepository extends JpaRepository<Lancha, Long> {
    boolean existsByNome(String nome);
}
