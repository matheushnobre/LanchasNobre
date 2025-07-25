package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.Cidade;
import com.matheushnobre.LanchasNobre.entity.Lancha;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    List<Viagem> findByLancha(Lancha lancha);

    Long countByCidadeOrigem(Cidade cidade);
    Long countByCidadeDestino(Cidade cidade);

    List<Viagem> findByCidadeOrigemAndCidadeDestinoAndDataPartidaBetween(Cidade cidadeOrigem, Cidade cidadeDestino, LocalDateTime dataPartidaInicio, LocalDateTime dataPartidaFim);
}
