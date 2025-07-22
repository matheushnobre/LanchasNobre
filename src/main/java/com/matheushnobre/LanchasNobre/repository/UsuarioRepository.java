package com.matheushnobre.LanchasNobre.repository;

import com.matheushnobre.LanchasNobre.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
