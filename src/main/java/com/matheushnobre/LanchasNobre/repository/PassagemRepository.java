package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.Assento;
import com.matheushnobre.LanchasNobre.entity.Passagem;
import com.matheushnobre.LanchasNobre.entity.Usuario;
import com.matheushnobre.LanchasNobre.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassagemRepository extends JpaRepository<Passagem, Long> {
    Passagem findByAssentoAndViagem(Assento assento, Viagem viagem);
    List<Passagem> findByPassageiro(Usuario usuario);
}
