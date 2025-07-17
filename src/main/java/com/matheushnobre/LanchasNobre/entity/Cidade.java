package com.matheushnobre.LanchasNobre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 2, nullable = false)
    private String estado;

    @JsonIgnore
    @OneToMany(mappedBy = "cidadeOrigem")
    private List<Viagem> viagensOrigem;

    @JsonIgnore
    @OneToMany(mappedBy = "cidadeDestino")
    private List<Viagem> viagensDestino;
}
